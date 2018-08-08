package com.common.security;

import com.alibaba.fastjson.JSONObject;
import com.common.cache.CacheClient;
import com.common.cache.CacheKeyConstant;
import com.common.entity.Constants;
import com.common.utils.EnvManager;
import com.common.utils.MD5Util;
import com.common.utils.StringUtil;
import com.common.utils.TokenUtil;
import com.pojo.User;
import com.pojo.UserToken;
import com.service.impl.UserSvcImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TokenAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    static final Logger logger = LoggerFactory.getLogger(TokenAuthenticationFilter.class);

    private boolean continueChainBeforeSuccessfulAuthentication = false;
    private SessionAuthenticationStrategy sessionStrategy = new NullAuthenticatedSessionStrategy();

    @Autowired
    private CacheClient cacheClient;

    // web认证有效时间，默认30分钟
    @Value("${user.web.auth.valid.time}")
    private int webAuthValidTime = 30;

    @Value("${user.app.auth.valid.time}")
    private int appAuthValidTime = 30 * 24 * 60;

    @Autowired
    private UserSvcImpl userSvcImpl;


    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        boolean isNewLog=false;
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        String userName;

        if (logger.isDebugEnabled()) {
            logger.debug("Request is to process authentication");
        }

        Authentication authResult;

        try {
            userName=checkToken(request,response);
            if (userName!=null){
                authResult =attemptAuthentication(request,response,userName);

            }else{
                authResult = attemptAuthentication(request, response);
                isNewLog=true;
                userName=obtainUsername(request);
            }
            if (authResult == null) {
                // return immediately as subclass has indicated that it hasn't completed authentication
                return;
            }
            sessionStrategy.onAuthentication(authResult, request, response);
        } catch(InternalAuthenticationServiceException failed) {
            logger.error("An internal error occurred while trying to authenticate the user.", failed);
            unsuccessfulAuthentication(request, response, failed);

            return;
        }
        catch (AuthenticationException failed) {
            // Authentication failed
            logger.error("An authenticationException occurred while trying to authenticate the user.", failed);
            unsuccessfulAuthentication(request, response, failed);

            return;
        }

        // Authentication success
        if (continueChainBeforeSuccessfulAuthentication) {
            chain.doFilter(request, response);
        }

        successfulAuthentication(request, response, chain, authResult);
        try {
            if(isNewLog){
                String token=userSvcImpl.generalToken(userName, CacheKeyConstant.TOKEN_PLATFORM,CacheKeyConstant.TOKEN_WEB_SIGNTYPE);
                TokenUtil.writeCookie(userName,token,request,response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response, String userName) throws AuthenticationException {

        User user= null;
        try {
            user = userSvcImpl.getUserByUserName(userName);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (user!= null) {
            String username = user.getUserName();
            String password = user.getPassword();

            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
            // Allow subclasses to set the "details" property
            setDetails(request, authRequest);

            return this.getAuthenticationManager().authenticate(authRequest);
        }

        return null;
    }

    // 异步刷新token静默时间
    private static ThreadPoolExecutor refreshAuthTimeThreadPool = new ThreadPoolExecutor(1, 5, 3, TimeUnit.SECONDS,
            new ArrayBlockingQueue<Runnable>(50), new ThreadPoolExecutor.DiscardPolicy());


    public String checkToken(HttpServletRequest request, HttpServletResponse response) {
        String requestURI = request.getRequestURI();
        String userName = null;
        String token = null;
        String signType= null;


        // 从cookie中读取登录态userName，token
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("userName")) {
                    userName = cookie.getValue();
                    continue;
                }
                if (cookie.getName().equals("token")) {
                    token = cookie.getValue();
                }
            }
        }

        signType = request.getParameter("signType");

        if (userName == null || token == null) {
            // 支持非cookie的情况, 认证成功回调，则会奖userName,token在httpget参数中带过来
            userName = request.getParameter("userName");
            token = request.getParameter("token");
        }


        //判断用户是否为空，或是黑名单用户
        if (StringUtils.isEmpty(userName)) {// && userSvcImpl.getUserByUserName(userName).getBlackUser() == 2
            //expiredAllCookie(request.getCookies(), response);

            return null;
        }



        if (userName == null || token == null ) {
            //expiredAllCookie(request.getCookies(), response);
            
            return null;
        }

        // 校验登录态是否有效
        boolean isAuth = isAuth(userName, token,signType);
        // 认证无效，引导用户登陆
        if (!isAuth) {
            //expiredAllCookie(request.getCookies(), response);

            return null;
        }

//		 打印请求参数（包括手机类型）
        printParams(request);
        return userName;
    }

    public static void expiredAllCookie(Cookie[] cs, HttpServletResponse response) {
        //muyudev.521meme.com
        String domain = ".521meme.com";
        String errDmain = "521meme.com";
        if (EnvManager.isIDCEnvCurrent()) {
            domain = ".521meme.com";
        }
        if (cs != null) {
            for (int i = 0; i < cs.length; i++) {
                Cookie c = new Cookie(cs[i].getName(), "");
                c.setDomain(domain);
                c.setMaxAge(0);
                c.setPath("/");
                response.addCookie(c);
                Cookie c1 = new Cookie(cs[i].getName(), "");
                c1.setDomain(errDmain);
                c1.setMaxAge(0);
                c1.setPath("/");
                response.addCookie(c1);
            }
        }
    }

    private void responseJson(HttpServletResponse response, JSONObject responseJSONObject) {
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.append(responseJSONObject.toString());
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    // 打印请求参数（包括手机类型）
    private void printParams(HttpServletRequest request) {
        Map<String, String[]> params = request.getParameterMap();
        String queryString = "";
        for (String key : params.keySet()) {
            if ("token".equals(key)) {
                continue;
            }
            String[] values = params.get(key);
            for (int i = 0; i < values.length; i++) {
                String value = values[i];
                if (value.length() > 50) {
                    // 如果参数太长了，搞短一点再打印
                    value = value.substring(0, 50) + "......";
                }
                queryString += key + "=" + value + "&";
            }
        }
        logger.info(request.getServletPath() + "?" + queryString + "&userAgent="
                + StringUtil.trim(request.getHeader("User-Agent")));

    }


    private boolean isAuth(String userName, String token, String signType) {
        try {
            if (userName == null || "".equals(userName) || token == null || "".equals(token) ) {
                return false;
            } else {
                String md5Key = MD5Util.md5(userName + "#" + token );
                // cache中校验登录态是否有效
                String TOKEN_KEY = Constants.FORUM_U_TOKEN_KEY + md5Key;
                Object obj = cacheClient.get(TOKEN_KEY);
                if (obj != null) {
                    UserToken userToken = (UserToken) obj;
                    // 区分web，app登陆方式不同的过期时间策略
                    long authValidTime = 30 * 60;
                    if (userToken.getSignType().equals("APP")) {
                        authValidTime = appAuthValidTime * 60;
                    } else {
                        authValidTime = webAuthValidTime * 60;
                    }

                    // 30分钟过期
                    if (System.currentTimeMillis() - userToken.getAuthTime().getTime()< authValidTime * 1000) {
                        // 有效可以考虑异步刷新userToken时间，到达session以静默时间为过期的效果
                        userToken.setAuthTime(new Date(System.currentTimeMillis()));
                        refreshAuthTimeThreadPool.execute(new RefreshAuthTimeTask(TOKEN_KEY, userToken));
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            }
        } catch (Exception e) {
            logger.error("判断认证是否有效发生异常：", e);
        }
        return false;
    }

    // 刷新token静默时间
    class RefreshAuthTimeTask implements Runnable {
        private String TOKEN_KEY;
        private UserToken userToken;

        public RefreshAuthTimeTask(String TOKEN_KEY, UserToken userToken) {
            this.TOKEN_KEY = TOKEN_KEY;
            this.userToken = userToken;
        }

        public void run() {
            try {
                // 区分web，app登陆方式不同的过期时间策略
                int authValidTime = 30 * 60;
                if (userToken.getSignType().equalsIgnoreCase("APP")) {
                    authValidTime = appAuthValidTime * 60;
                } else {
                    authValidTime = webAuthValidTime * 60;
                }
                cacheClient.set(TOKEN_KEY, authValidTime, userToken, 500);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    public static Logger getLogger() {
        return logger;
    }

    public boolean isContinueChainBeforeSuccessfulAuthentication() {
        return continueChainBeforeSuccessfulAuthentication;
    }

    @Override
    public void setContinueChainBeforeSuccessfulAuthentication(boolean continueChainBeforeSuccessfulAuthentication) {
        this.continueChainBeforeSuccessfulAuthentication = continueChainBeforeSuccessfulAuthentication;
    }

    public SessionAuthenticationStrategy getSessionStrategy() {
        return sessionStrategy;
    }

    public void setSessionStrategy(SessionAuthenticationStrategy sessionStrategy) {
        this.sessionStrategy = sessionStrategy;
    }

    public CacheClient getCacheClient() {
        return cacheClient;
    }

    public void setCacheClient(CacheClient cacheClient) {
        this.cacheClient = cacheClient;
    }

    public int getWebAuthValidTime() {
        return webAuthValidTime;
    }

    public void setWebAuthValidTime(int webAuthValidTime) {
        this.webAuthValidTime = webAuthValidTime;
    }

    public int getAppAuthValidTime() {
        return appAuthValidTime;
    }

    public void setAppAuthValidTime(int appAuthValidTime) {
        this.appAuthValidTime = appAuthValidTime;
    }

    public UserSvcImpl getUserSvcImpl() {
        return userSvcImpl;
    }

    public void setUserSvcImpl(UserSvcImpl userSvcImpl) {
        this.userSvcImpl = userSvcImpl;
    }

    public static ThreadPoolExecutor getRefreshAuthTimeThreadPool() {
        return refreshAuthTimeThreadPool;
    }

    public static void setRefreshAuthTimeThreadPool(ThreadPoolExecutor refreshAuthTimeThreadPool) {
        TokenAuthenticationFilter.refreshAuthTimeThreadPool = refreshAuthTimeThreadPool;
    }
}
