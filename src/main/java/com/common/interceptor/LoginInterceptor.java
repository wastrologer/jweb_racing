package com.common.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.common.cache.CacheClient;
import com.common.entity.Constants;
import com.common.utils.EnvManager;
import com.common.utils.MD5Util;
import com.common.utils.StringUtil;
import com.constant.ErrorCode;
import com.pojo.UserToken;
import com.service.IUserSvc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 前端用户登录
 * 
 * @author Admin
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {

	static final Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);
	
	@Autowired
	private CacheClient cacheClient;
	
	// web认证有效时间，默认30分钟
	@Value("${user.web.auth.valid.time}")
	private int webAuthValidTime = 30;
	
	@Value("${user.app.auth.valid.time}")
	private int appAuthValidTime = 30 * 24 * 60;
	
	@Autowired
	private IUserSvc userSvcImpl;
	
	
	// 异步刷新token静默时间
	private static ThreadPoolExecutor refreshAuthTimeThreadPool = new ThreadPoolExecutor(1, 5, 3, TimeUnit.SECONDS,
			new ArrayBlockingQueue<Runnable>(50), new ThreadPoolExecutor.DiscardPolicy());

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String requestURI = request.getRequestURI();
		String userName = null;
		String utoken = null;
		String signType= null;
		
		
		// 从cookie中读取登录态userName，utoken
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("userName")) {
					userName = cookie.getValue();
					continue;
				}
				if (cookie.getName().equals("utoken")) {
					utoken = cookie.getValue();
				}
			}
		}
		
		signType = "web";//request.getParameter("signType");
		
		if (userName == null || utoken == null) {
			// 支持非cookie的情况, 认证成功回调，则会奖userName,utoken在httpget参数中带过来
			userName = request.getParameter("userName");
			utoken = request.getParameter("utoken");
		}

		if (isNoNeedLoginUrl(requestURI)) {
			printParams(request);
			return true;
		}
		
		//判断用户是否是黑名单用户
		if (userName != null && userSvcImpl.getUserByUserName(userName).getIsDisable() == 1) {
			JSONObject responseJSONObject = new JSONObject();
			responseJSONObject.put("errorCode", ErrorCode.IS_BLACK_USER);
			responseJSONObject.put("data", null);
			responseJSONObject.put("msg", "黑名单用户");
			expiredAllCookie(request.getCookies(), response);
			responseJson(response, responseJSONObject);
			return false;
		}
		
		

		
		if (userName == null || utoken == null || signType == null) {
			JSONObject responseJSONObject = new JSONObject();
			responseJSONObject.put("errorCode", ErrorCode.USER_LOGIN_ERROR_UNLOGIN);
			responseJSONObject.put("data", null);
			responseJSONObject.put("msg", "还未登录");
			expiredAllCookie(request.getCookies(), response);
			responseJson(response, responseJSONObject);
			return false;
		}
		
		// 校验登录态是否有效
		boolean isAuth = isAuth(userName, utoken,signType);
		// 认证无效，引导用户登陆
		if (!isAuth) {
			JSONObject responseJSONObject = new JSONObject();
			responseJSONObject.put("errorCode", ErrorCode.USER_LOGIN_ERROR_UTOKEN);
			responseJSONObject.put("data", null);
			responseJSONObject.put("msg", "登录失效");
			expiredAllCookie(request.getCookies(), response);
			responseJson(response, responseJSONObject);
			return false;
		}
		
//		 打印请求参数（包括手机类型）
		printParams(request);
		return true;
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
			if ("utoken".equals(key)) {
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
	
	
	private boolean isAuth(String userName, String utoken, String signType) {
		try {
			if (userName == null || "".equals(userName) || utoken == null || "".equals(utoken) || signType == null || "".equals(signType)) {
				return false;
			} else {
				String md5Key = MD5Util.md5(userName + "#" + utoken );
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
						authValidTime = appAuthValidTime * 60;//webAuthValidTime
					}
					
					// 30分钟过期
					if (System.currentTimeMillis() - userToken.getLastRefreshTime() < authValidTime * 1000) {
						// 有效可以考虑异步刷新userToken时间，到达session以静默时间为过期的效果
						userToken.setLastRefreshTime(System.currentTimeMillis());
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
	
	private boolean isNoNeedLoginUrl(String requestURI) {
        boolean flag = true;

        if (requestURI.contains("/customer/") ) {
            flag =false ;
        }

        return flag;
    }
}
