package com.controller;

import com.alibaba.fastjson.JSONObject;
import com.common.cache.CacheClient;
import com.common.entity.Constants;
import com.common.entity.SMSVo;
import com.common.utils.StringUtil;
import com.common.utils.TokenUtil;
import com.constant.ErrorCode;
import com.pojo.User;
import com.pojo.UserToken;
import com.service.IUserSvc;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/login")
public class LoginController extends BaseController {
    protected static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Value("${baseLoginUrl}")
    private String baseLoginUrl = "http://localhost:8080";

    @Autowired
    private IUserSvc userSvcImpl;

    @Autowired
    private CacheClient cacheClient;

    @Value("${user.register.isauthcode}")
    private String isNeedAuthCode = "2"; // 登录是否需要验证码 1否(0000可做为万能验证码) 2是

    @Value("${company.account.number}")
    private String companyAccountNumber = "";


    @RequestMapping(value = "/logout", method =  { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> logout(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            return      getErrorMap();
        }
        return        getSuccessMap();
    }


    /**
     * 短信登陆
     * @param signType 登陆方式：APP ,WEB
     * @param telephone	电话
     * @param smsCode	验证码
     * @param inviteCode 邀请码
     * @return
     */
    @RequestMapping(value = "/smsLogin", method = { RequestMethod.POST, RequestMethod.GET })
    @ResponseBody
    public Map<String, Object> smsLogin(String signType,String telephone, String smsCode, String inviteCode,
                                        String deviceIdentifier, Double longitude, Double latitude, String deviceDesc,String type){
        logger.info("用户短信登陆     signType:" + signType
                +",telephone:" + telephone
                +",smsCode:" + smsCode
                +",inviteCode:" + inviteCode
        );
        Map<String, Object> dataMap = new HashMap<String, Object>();
        if (StringUtil.isEmpty(telephone) || StringUtil.isEmpty(smsCode) || StringUtil.isEmpty(signType)) {
            dataMap.put("errCode", ErrorCode.PARAM_IS_NULL);
            dataMap.put("msg", "参数为空");
            return dataMap;
        }

        String u_token = null;
        try {
            boolean flag = Pattern.matches(Constants.REG_MOBILE, telephone);
            if (!flag) {
                dataMap.put("errCode", ErrorCode.USER_TELPHONE_FORMAT_ERROR);
                dataMap.put("msg", "手机号码格式不正确");
                return dataMap;
            }

            String smsKey = Constants.SMS_LOGIN_PASSWD_KEY_STR + telephone;
            SMSVo smsVo = (SMSVo) cacheClient.get(smsKey);
            if (isNeedAuthCode.equals("1") || smsCode.equals("0000")) {

            } else if (smsVo == null) {
                dataMap.put("errorCode", ErrorCode.USER_ERROR_SMS_CODE);
                dataMap.put("msg", "短信验证码已过期，请重新发送并验证");
                return dataMap;
            } else if (!smsVo.getContent().contains(smsCode)) {
                dataMap.put("errorCode", ErrorCode.USER_ERROR_SMS_CODE);
                dataMap.put("msg", "短信验证码错误");
                logger.info("验证码错误:" + smsVo.getContent() + "--" + smsCode);
                return dataMap;
            }
            u_token = userSvcImpl.generalToken(telephone, "kinder",signType);
            dataMap.put("errorCode", 1);
            dataMap.put("userName", telephone);
            dataMap.put("utoken", u_token);

        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("errorCode", -1);
            dataMap.put("msg", "短信异常:" + e.getMessage());
            logger.error(e.getMessage(), e);
        }
        return dataMap;
    }


    @RequestMapping(value = "/auth", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> auth(@RequestParam("userName") String userName, @RequestParam("isLogin") int isLogin,
                                    @RequestParam(value = "platform", required = false) String platform,
                                    @RequestParam(value = "signType", required = false) String signType, HttpServletRequest request) {
        Map<String, Object> map = getSuccessMap();
        try {
            HttpClient client = new HttpClient();
            if (platform == null || platform.equals("")) {
                platform = "kinder";
            }
            if (signType == null || signType.equals("")) {
                signType = "WEB";
            }
            String url = baseLoginUrl + "/login/getToken?userName=" + userName + "&platform=" + platform + "&signType=" + signType;
            HttpMethodBase method = null;
            if (request.getMethod().equals("GET")) {
                method = new GetMethod(url);
            } else {
                method = new PostMethod(url);
            }

            Map<String, Object> detail = new HashMap<>();
            User user = userSvcImpl.getUserByPhoneNumber(userName);
            if (user == null) {
                map.put("errorCode", ErrorCode.USER_NOT_EXIST);
                map.put("msg", "用户不存在！");
                return map;
            }
            //User u=userSvcImpl.getUserByUserName(data.getString("userName"));
            if(user.getIsDisable()!=2){
                return getErrorMap(ErrorCode.IS_BLACK_USER,"黑名单用户");
            }
            if (isLogin == 1) {
                method.addRequestHeader("Authorization", request.getHeader("Authorization"));
            }
            int statusCode = client.executeMethod(method);
            if (statusCode == HttpStatus.SC_UNAUTHORIZED) {
                if (isLogin == 1) {
                    System.out.println("认证失败");
                    logger.error("认证失败");
                    //认证失败，记录于用户登陆失败信息
                    if (user != null) {
                        map.put("errorCode", ErrorCode.USER_AND_PASSWORD_ERROR);
                        map.put("msg", "账号密码不正确！" );
                        return map;
                    }
                }
                Header header = method.getResponseHeader("WWW-Authenticate");
                HeaderElement[] elements = header.getElements();
                for (HeaderElement element : elements) {
                    detail.put(element.getName(), element.getValue());
                }
                detail.put("realm", detail.get("Digest realm"));
                map.put("data", detail);
            } else if (statusCode == HttpStatus.SC_OK) {
                JSONObject data = JSONObject.parseObject(method.getResponseBodyAsString());
                map.put("errorCode", Integer.valueOf(data.getString("errorCode")));
                detail.put("userName", data.getString("userName"));
                detail.put("utoken", data.getString("token"));
                map.put("data", detail);
                //认证成功，清楚用户登录失败信息
            } else {
                logger.error("认证失败");
                // throw new BusinessException(ErrorEnum.AUTH_FAILURE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return map;
    }

    /**
     * 认证通过后，生成一个token返回客户端
     *
     * @param userName
     * @param platform
     * @return
     */
    @RequestMapping(value = "/getToken", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> getToken(@RequestParam("userName") String userName,
                                             @RequestParam(value = "platform", required = false) String platform,
                                             @RequestParam(value = "signType", required = false) String signType) {
        Map<String, Object> dataMap = getSuccessMap();
        if (platform == null || platform.equals("")) {
            platform = "kinder";
        }
        if (signType == null || signType.equals("")) {
            signType = "WEB";
        } else {
            signType = signType.toUpperCase();
        }
        String u_token = null;
        try {
            u_token = userSvcImpl.generalToken(userName, "kinder", signType);
            dataMap.put("errorCode", 1);
            dataMap.put("userName", userName);
            dataMap.put("token", u_token);
        } catch (Exception e) {
            dataMap.put("errorCode", -1);
            logger.error(e.getMessage(), e);
        }
        return dataMap;
    }

    /**
     * 接受认证后的回调，将用户和utoken存储在当前用户cookie中，跳转至首页
     * 此处不做认证是否有效的校验，统一由LoginInterceptor去做拦截校验
     */
    @RequestMapping(value = "/writeCookie", method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> writeCookie(String userName, String utoken, HttpServletRequest request,
                                        HttpServletResponse response) {

        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            User user = userSvcImpl.getUserByUserName(userName);
            if (user != null ) {
                Map<String, Object> data = new HashMap<String, Object>();
                data.put("userId", user.getUserId());
                data.put("authId", user.getAuthId());

                dataMap.put("errorCode", 1);
                dataMap.put("msg", "成功");
                dataMap.put("data", data);

                //写入cookie
                TokenUtil.writeCookie(userName, request, response, utoken);
                //update login time
                User pu=new User();
                pu.setUserId(user.getUserId());
                pu.setLoginTime(new Timestamp(System.currentTimeMillis()));
                userSvcImpl.updateUserOnly(pu);
            }else{
                dataMap.put("errorCode", -1);
                dataMap.put("msg", "用户账号不正确");
            }
        } catch (Exception e) {
            logger.error("exception:", e);
            dataMap.put("errorCode", -1);
            dataMap.put("msg", "登录异常:" + e.getMessage());
        }
        return dataMap;
    }

    /**
     *
     */
    @RequestMapping(value = "/judgeLogined", method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> judgeLogined() {

        Map<String, Object> dataMap = new HashMap<String, Object>();
        Map<String, Object> data = new HashMap<String, Object>();
        try {
            UserToken uk=getUserToken();
            if(uk!=null){
                User user= userSvcImpl.getUserById((int)uk.getUserId());
                if (user != null ) {
                    data.put("isLogined",true);
                    dataMap.put("errorCode", 1);
                    dataMap.put("msg", "成功");
                    dataMap.put("data", data);
                    return  dataMap;
                }
            }
        } catch (Exception e) {
            logger.error("exception:", e);
            dataMap.put("errorCode", -1);
            dataMap.put("msg", "登录异常:" + e.getMessage());
        }
        data.put("isLogined",false);
        dataMap.put("errorCode", 1);
        dataMap.put("msg", "成功");
        dataMap.put("data", data);
        return dataMap;
    }


}
