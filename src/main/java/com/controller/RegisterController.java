package com.controller;

import com.common.cache.CacheClient;
import com.common.entity.CommonEnum;
import com.common.entity.Constants;
import com.common.entity.ReturnValueConstants;
import com.common.entity.SMSVo;
import com.common.jiyan.GeetestConfig;
import com.common.jiyan.sdk.GeetestLib;
import com.common.utils.SendSMS;
import com.common.utils.StringUtil;
import com.constant.ErrorCode;
import com.pojo.User;
import com.service.IUserSvc;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/reg")
public class RegisterController extends BaseController {
    protected static final Logger logger = LoggerFactory.getLogger(RegisterController.class);

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


//    @RequestMapping(value = "/getValidateImg", method =  { RequestMethod.GET, RequestMethod.POST })
//
//    public String getValidateImg(HttpServletRequest request, HttpServletResponse response) {
//        try {
//
//            request.setCharacterEncoding("utf-8");
//            response.setContentType("text/html;charset=utf-8");
//
//            // 设置响应的类型格式为图片格式
//            response.setContentType("image/jpeg");
////			response.setHeader("content-type", "image/jpeg;charset=UTF-8");
//            //禁止图像缓存。
//            response.setHeader("Pragma", "No-cache");
//            response.setHeader("Cache-Control", "no-cache");
//            response.setDateHeader("Expires", 0);
//
//
//            String ip = getIpAddr(request);
//            GetsResponse<Object> smsCountVoGetsResponse = cacheClient.gets(Constants.IMAGE_CODE_Count_KEY_STR + ip);
//            ImageCodeCount ImageCodeCount;
//
//            ImageValidateCode vCode = null;
//            if (smsCountVoGetsResponse == null) {
//                vCode = new ImageValidateCode(135,38,5,200);
//
//                ImageCode imc = new ImageCode();
//                imc.setContent(vCode.getImageCode());
//                imc.setTimestamp(HelperUtil.getSecondStamp());
//
//                ImageCodeCount = new ImageCodeCount();
//                ImageCodeCount.setCount(1);
//                ImageCodeCount.setTimestamp(HelperUtil.getSecondStamp());
//                cacheClient.set(Constants.IMAGE_CODE_DETAIL_KEY_STR + ip, Constants.IMAGE_CODE_EXPIRE_SECOND, imc);
//                cacheClient.set(Constants.IMAGE_CODE_Count_KEY_STR + ip, Constants.IMAGE_CODE_COUNT_EXPIRE_SECOND, ImageCodeCount);
//            }else{
//                ImageCodeCount = (ImageCodeCount) smsCountVoGetsResponse.getValue();
//                if (ImageCodeCount.getCount() < Constants.DEFAULT_DAY_MAX_IMAGE_CODE) {
//                    ImageCode imc = (ImageCode) cacheClient.get(Constants.IMAGE_CODE_DETAIL_KEY_STR + ip);
//                    vCode = new ImageValidateCode(135,38,5,200);
//                    if (imc == null) {
//                        imc = new ImageCode();
//                        imc.setTimestamp(HelperUtil.getSecondStamp());
//                        imc.setContent(vCode.getImageCode());
//                    } else {
//                        imc.setContent(vCode.getImageCode());
//                        imc.setTimestamp(HelperUtil.getSecondStamp());
//                    }
//                    ImageCodeCount.setCount(ImageCodeCount.getCount() + 1);
//                    cacheClient.set(Constants.IMAGE_CODE_DETAIL_KEY_STR + ip, Constants.SMS_SEND_EXPIRE_SECOND, imc);
//                    cacheClient.cas(Constants.IMAGE_CODE_Count_KEY_STR + ip,
//                            (int) ((24 * 3600) - (HelperUtil.getSecondStamp() - ImageCodeCount.getTimestamp())),
//                            ImageCodeCount, smsCountVoGetsResponse.getCas());
//                }else{
//                    String info="访问次数超过最大数";
//                    logger.info(info);
//                    ImageValidationUtil.writeOutputInfo(info,response);
//                    return null;
//                }
//            }
//            vCode.write(response.getOutputStream());
//            vCode = null;
//        } catch (Exception e) {
//            String info="获取图片验证码异常";
//            logger.info(  info+e.getMessage());
//            ImageValidationUtil.writeOutputInfo(info,response);
//        }
//        return "";
//    }
    
    /**
	 * 获取滑动图形验证码
	 * @return
	 */
	@RequestMapping(value="/getValidateImg")
	public String imageValidateCode(HttpServletRequest request,HttpServletResponse response ,String telephone,String clientType) throws Exception{
		try {
			GeetestLib gtSdk = new GeetestLib(GeetestConfig.getGeetest_id(), GeetestConfig.getGeetest_key(), 
					GeetestConfig.isnewfailback());
	
			String resStr = "{}";
			
			//自定义userid
			String userid = telephone;
			
			//自定义参数,可选择添加
			HashMap<String, String> param = new HashMap<String, String>(); 
			param.put("user_id", telephone); //网站用户id
			param.put("client_type", clientType); //web:电脑上的浏览器；h5:手机上的浏览器，包括移动应用内完全内置的web_view；native：通过原生SDK植入APP应用的方式
			param.put("ip_address", "127.0.0.1"); //传输用户请求验证时所携带的IP
	
			//进行验证预处理
			int gtServerStatus = gtSdk.preProcess(param);
			
			//将服务器状态设置到session中
			request.getSession().setAttribute(gtSdk.gtServerStatusSessionKey, gtServerStatus);
			//将userid设置到session中
			request.getSession().setAttribute("userid", userid);
			
			resStr = gtSdk.getResponseStr();
	
			PrintWriter out = response.getWriter();
			out.println(resStr);
			}catch (Exception e) {
				logger.info("获取滑动验证图片异常：" + e.getMessage());
		}
		return null;
	}
    
    
    
    /**
     * 发送登录短信验证码
     *
     * @param telephone
     * @param smsUseType 验证码使用类型：1注册使用  2短信登陆使用  3忘记密码使用
     * @return
     */
    @RequestMapping(value = "/smsValidation", method =  { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> smsValidation(String telephone,
                                             @RequestParam(required=false)String signType,
                                             @RequestParam(required=false)Byte smsUseType
                                             ,String clientType) {
        Map<String, Object> map = getSuccessMap();
        try {
            if (StringUtil.isEmpty(telephone) ) {
                map.put("errorCode", ErrorCode.PARAM_IS_NULL);
                map.put("msg", "参数为空");
                return map;
            }
/*            if (signType.equalsIgnoreCase("APP")) {
                if (compareVersion("1.0.3", "1.0.2") > 0) {
                    if (smsUseType == null) {
                        map.put("errorCode", ErrorCode.PARAM_IS_NULL);
                        map.put("msg", "参数为空");
                        return map;
                    }
                }
            }else{
                if (smsUseType == null) {
                    map.put("errorCode", ErrorCode.PARAM_IS_NULL);
                    map.put("msg", "参数为空");
                    return map;
                }
            }*/


            //===================2018-01-24 添加图片验证码=========================//
//            String ip = getNewIpAddr(request);//相同ip？
//            String imageKey = Constants.IMAGE_CODE_DETAIL_KEY_STR + ip;
//            ImageCode imc = (ImageCode) cacheClient.get(imageKey);
//            if (imc == null) {
//                map.put("errorCode", ErrorCode.IMAGE_CODE_NOT_EXIST_ERROR);
//                map.put("msg", "图片证码已过期，请重新获取");
//                return map;
//            } else if (!StringUtils.equalsIgnoreCase(imageCode, imc.getContent())) {
//                map.put("errorCode", ErrorCode.IMAGE_CODE_ERROR);
//                map.put("msg", "图片验证码错误");
//                logger.info("图片验证码错误:" + imc.getContent() + "--" + imageCode);
//                return map;
//            }
            //===================2018-01-24 添加图片验证码=========================//
            
          //===========================滑动验证码===============================//
			if (clientType == null) {
				map.put("errCode", ErrorCode.PARAM_IS_NULL);
				map.put("msg", "参数为空");
				return map;
			}
			GeetestLib gtSdk = new GeetestLib(GeetestConfig.getGeetest_id(), GeetestConfig.getGeetest_key(), 
					GeetestConfig.isnewfailback());
			
			String challenge = request.getParameter(GeetestLib.fn_geetest_challenge);
			String validate = request.getParameter(GeetestLib.fn_geetest_validate);
			String seccode = request.getParameter(GeetestLib.fn_geetest_seccode);
			
			//从session中获取gt-server状态
			int gt_server_status_code = (Integer) request.getSession().getAttribute(gtSdk.gtServerStatusSessionKey);
			
			//从session中获取userid
			String userid = (String)request.getSession().getAttribute("userid");
			
			//自定义参数,可选择添加
			HashMap<String, String> param = new HashMap<String, String>(); 
			param.put("user_id", telephone); //网站用户id
			param.put("client_type", clientType); //web:电脑上的浏览器；h5:手机上的浏览器，包括移动应用内完全内置的web_view；native：通过原生SDK植入APP应用的方式
			param.put("ip_address", "127.0.0.1"); //传输用户请求验证时所携带的IP
			
			int gtResult = 0;
			
			if (gt_server_status_code == 1) {
				//gt-server正常，向gt-server进行二次验证
				
				gtResult = gtSdk.enhencedValidateRequest(challenge, validate, seccode, param);
				System.out.println(gtResult);
			} else {
				// gt-server非正常情况下，进行failback模式验证
				
				System.out.println("failback:use your own server captcha validate");
				gtResult = gtSdk.failbackValidateRequest(challenge, validate, seccode);
				System.out.println(gtResult);
			}
			
			
			if (gtResult == 1) {
//			// 验证成功
//			PrintWriter out = response.getWriter();
//			JSONObject data = new JSONObject();
//			try {
//				data.put("status", "success");
//				data.put("version", gtSdk.getVersionInfo());
//			} catch (JSONException e) {
//				e.printStackTrace();
//			}
//			out.println(data.toString());
			}
			else {
				map.put("errCode", ErrorCode.IMAGE_CODE_ERROR);
				map.put("msg", "滑动验证失败");
				logger.info("滑动验证失败");
				return map;
			}
			//===========================滑动验证码===============================//
            

            boolean flag = Pattern.matches(Constants.REG_MOBILE, telephone);
            if (!flag) {
                map.put("errorCode", ErrorCode.USER_TELPHONE_FORMAT_ERROR);
                map.put("msg", "手机号码格式不正确");
                return map;
            }

//			

            String smsCountKey = Constants.SMS_LOGIN_PASSWD_COUNT_KEY_STR + telephone;
            String smsKey = Constants.SMS_LOGIN_PASSWD_KEY_STR + telephone;
            String code = SendSMS.generateValiateCode();
            SendSMS.sendUCP(telephone, map, smsCountKey, smsKey, new String[] { code,
                    Constants.SMS_VALIDATE_CODE_LIMIT_TIME }, cacheClient, CommonEnum.SMSTemplateId.BINDINGSMS_CODE.value);
            logger.info("登陆验证码 code:" + code);

//			cacheClient.set(imageKey, 0, imc);
//            cacheClient.delete(imageKey);
        } catch (Exception e) {
            logger.error("exception:", e);
            map.put("errorCode", -1);
            map.put("msg", "异常:" + e.getMessage());
        }
        return map;
    }

 /*   *//**
     * 认证通过后，生成一个token返回客户端
     *
     * @param userName
     * @param platform
     * @return
     *//*
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
            dataMap.put("errorCode", 0);
            dataMap.put("userName", userName);
            dataMap.put("token", u_token);
        } catch (Exception e) {
            dataMap.put("errorCode", -1);
            logger.error(e.getMessage(), e);
        }
        return dataMap;
    }*/


/*    *//**
     * 接受认证后的回调，将用户和utoken存储在当前用户cookie中，跳转至首页
     * 此处不做认证是否有效的校验，统一由LoginInterceptor去做拦截校验
     *//*
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
                dataMap.put("errorCode", 0);
                dataMap.put("msg", "成功");
                dataMap.put("data", data);

                //写入cookie
                TokenUtil.writeCookie(userName, request, response, utoken);
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
    }*/

    /**
     * 判断手机号是否符合要求
     * @param telephone
     * @param smsUseType 验证码使用类型：1注册使用  2短信登陆使用  3忘记密码使用
     * @return
     */
    @RequestMapping(value="/validatePhoneNumber",method={RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> validatePhoneNumber(String telephone,
                                                   @RequestParam(required=true)Integer smsUseType){
        Map<String, Object> map = getSuccessMap();
        map.put("msg", "手机号可用");
        try {
            if(smsUseType==null||StringUtil.isEmpty(telephone)){
                map.put("errorCode", ErrorCode.PARAM_IS_NULL);
                map.put("msg", "参数为空");
                return map;
            }
            if(smsUseType!=1&&smsUseType!=2&&smsUseType!=3){
                map.put("errorCode", ErrorCode.PARAM_ERROR);
                map.put("msg", "参数错误");
                return map;
            }
            boolean flag = Pattern.matches(Constants.REG_MOBILE, telephone);
            if (!flag) {
                map.put("errorCode", ErrorCode.USER_TELPHONE_FORMAT_ERROR);
                map.put("msg", "手机号码格式不正确");
                return map;
            }
            User user = userSvcImpl.getUserByPhoneNumber(telephone);
            if(user!=null&&smsUseType==1){
                map.put("errorCode", ErrorCode.USER_TELEPHONE_EXIST_ERROR);
                map.put("msg", "手机号已注册");
                return map;
            }else if(user==null&&(smsUseType==2||smsUseType==3)){
                map.put("errorCode", ErrorCode.USER_TELEPHONE_NOT_EXIST_ERROR);
                map.put("msg", "手机号未注册");
                return map;
            }
        } catch (Exception e) {
            logger.error("exception:", e);
            map.put("errorCode", -1);
            map.put("msg", "异常:" + e.getMessage());
        }
        return map;
    }

    /**
     * 判断用户名是否符合要求
     * @param userName
     * @return
     */
    @RequestMapping(value="/validateUserName",method={RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> validateUserName(String userName){
        Map<String, Object> map = getSuccessMap();
        try {
            if (StringUtils.isEmpty(userName)) {
                map.put("msg", "默认使用手机号码");
                return map;
            }
            boolean flag = Pattern.matches(Constants.USERNAME_MOBILE, userName);
            if (!flag) {
                map.put("errorCode", ErrorCode.USER_USERNAME_FORMAT_ERROR);
                map.put("msg", "用户名格式不正确");
                return map;
            }
            User user = userSvcImpl.getUserByUserName(userName);
            if(user==null){
                map.put("msg", "用户名可用");
                return map;
            }else {
                map.put("errorCode", ErrorCode.USER_USERNAME_EXIST_ERROR);
                map.put("msg", "用户名已存在");
                return map;
            }
        } catch (Exception e) {
            logger.error("exception:", e);
            map.put("errorCode", -1);
            map.put("msg", "操作异常:" + e.getMessage());
        }
        return map;
    }


    /**
     * 判断密码是否符合要求
     * @param password
     * @return
     */
    @RequestMapping(value="/validatePassword",method={RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> validatePassword(String password){
        Map<String, Object> map = getSuccessMap();
        try {
            if (StringUtils.isEmpty(password)) {
                map.put("errorCode", ErrorCode.PARAM_IS_NULL);
                map.put("msg", "参数为空");
                return map;
            }
            boolean flag = Pattern.matches(Constants.PASSWORD_MOBILE, password);
            if (!flag) {
                map.put("errorCode", ErrorCode.USER_PASSWORD_FORMATE_ERROR);
                map.put("msg", "密码格式不正确");
                return map;
            }
            map.put("msg", "密码可用");
        } catch (Exception e) {
            logger.error("exception:", e);
            map.put("errorCode", -1);
            map.put("msg", "操作异常:" + e.getMessage());
        }
        return map;
    }

    /**
     * 判断smsCode是否符合要求
     * @param smsCode
     * @return
     */
    public Map<String, Object> validateSmsCode(String smsCode, String telephone){
        Map<String, Object> map = getSuccessMap();
        try {
            if (StringUtils.isEmpty(smsCode)) {
                map.put("errorCode", ErrorCode.PARAM_IS_NULL);
                map.put("msg", "参数为空");
                return map;
            }
            String smsKey = Constants.SMS_LOGIN_PASSWD_KEY_STR + telephone;
            SMSVo smsVo = (SMSVo) cacheClient.get(smsKey);
            Boolean a=isNeedAuthCode.equals("1");
            System.out.println(isNeedAuthCode);
            Boolean aa= smsCode.equals("0000");
            if (isNeedAuthCode.equals("1") && smsCode.equals("0000") || (companyAccountNumber.equals("1") )) {

            } else if (smsVo == null) {
                map.put("errorCode", ErrorCode.USER_ERROR_SMS_CODE);
                map.put("msg", "短信验证码已过期，请重新发送并验证");
                return map;
            } else if (!smsVo.getContent().contains(smsCode)) {
                map.put("errorCode", ErrorCode.USER_ERROR_SMS_CODE);
                map.put("msg", "短信验证码错误");
                logger.info("验证码错误:" + smsVo.getContent() + "--" + smsCode);
                return map;
            }
        } catch (Exception e) {
            logger.error("exception:", e);
            map.put("errorCode", -1);
            map.put("msg", "操作异常:" + e.getMessage());
        }
        return map;
    }

    /**
     * 判断inviteCode是否符合要求
     * @param inviteCode
     * @return
     */
    @RequestMapping(value="/validateInviteCode",method={RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> validateInviteCode(String inviteCode){
        Map<String, Object> map = getSuccessMap();
        try {
            if (StringUtils.isEmpty(inviteCode)) {
                map.put("msg", "inviteCode为空");
                return map;
            }
            boolean flag = Pattern.matches(Constants.INVITECODE_MOBILE, inviteCode);
            if (!flag) {
                map.put("errorCode", ErrorCode.USER_USERNAME_FORMAT_ERROR);
                map.put("msg", "邀请码格式不正确");
                return map;
            }
            User user = userSvcImpl.getUserByInviteCode(inviteCode);
            if(user!=null){
                map.put("msg", "邀请码可用");
                return map;
            }else {
                map.put("errorCode", ErrorCode.USER_USERNAME_EXIST_ERROR);
                map.put("msg", "邀请码不存在");
                return map;
            }
        } catch (Exception e) {
            logger.error("exception:", e);
            map.put("errorCode", -1);
            map.put("msg", "操作异常:" + e.getClass());
        }
        return map;
    }
/*    @RequestMapping(value = "/createUser1", method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public  Map<String, Object> createUser1() {
        for(int i=10;i<60;i++){
        String url="http://localhost:8080/proxy/reg/createUser?password=a1234567&smsCode=0000&phoneNumber=137222222";
            //user.setPhoneNumber("137222222"+i);
            //rc.createUser(user,"0000",null,null,null,null,null);
            url=url+i;
            doGet(url);
        }
        return null;
    }*/
    /**
     * 创建用户
     *
     * @return
     */
    @RequestMapping(value = "/createUser", method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public  Map<String, Object> createUser(User user,String smsCode,
                                          String inviteCode, String deviceIdentifier, Double longitude, Double latitude, String deviceDesc) {
        logger.info("注册用户, createUser, telephone=" + user.getPhoneNumber() + ", password=" + user.getPassword() + ", smsCode=" + smsCode
                + ", inviteCode=" + inviteCode);
        Map<String, Object> map = getSuccessMap();
        Map<String, Object> result = null;

        try {
            //判断smsCode是否符合要求
            result= validateSmsCode(smsCode,user.getPhoneNumber());
            if(result.get("errorCode")==null||(Integer)result.get("errorCode")!=1){
                return result;
            }
/*            //判断用户名是否符合要求
            result= validateUserName(user.getPhoneNumber());
            if(result.get("errorCode")==null||(Integer)result.get("errorCode")!=1){
                Integer i=(Integer)result.get("errorCode");
                if(i!=1)
                return result;
            }*/
            //判断telephone是否符合要求
            result= validatePhoneNumber(user.getPhoneNumber(),1);
            user.setUserName(user.getPhoneNumber());
            //int errorCode=(int)result.get("errorCode");
            if(result.get("errorCode")==null||(Integer)result.get("errorCode")!=1){
                return result;
            }
            //判断密码是否符合要求
            result= validatePassword(user.getPassword());
            if(result.get("errorCode")==null||(Integer)result.get("errorCode")!=1){
                return result;
            }
            //邀请码判定
            User inviteMan=null;
            if(!StringUtil.isEmpty(user.getInviteCode())){
                result= validateInviteCode(user.getInviteCode());
                Integer i=(Integer)result.get("errorCode");
                if(i!=1)
                    return result;
                else {
                    inviteMan=userSvcImpl.getUserByInviteCode(user.getInviteCode());
                    user.setInviteUserId(inviteMan.getUserId());
                }
            }

            int iValue = userSvcImpl.addUser(user);
            if (iValue <= ReturnValueConstants.OPERATE_INSERT_ZERO) {
                map.put("errorCode", -1);
                map.put("msg", "注册用户数据库异常");
                return map;
            }
            //使用邀请码注册
            if(!StringUtil.isEmpty(user.getInviteCode())){
            User pu=new User();
            pu.setUserId(inviteMan.getUserId());
            pu.setInviteNum(1);//add coin in it
            //pu.setUserCoin(Constants.INVITE_COIN_REWARD);
            userSvcImpl.updateUserWithCoinAndGold(pu);
            }
/*            //如果使用邀请码注册：通知mq进行邀请相关操作
            if (code != null) {
                ReturnMessage returnMessage = new ReturnMessage();
                returnMessage.setInviteUsreId(user.getUid());//被邀请人id
                returnMessage.setUserId(code.getUserId());	//邀请人id
                msgProducer.send(MsgCommandId.DIDI_INVITE_AWARD_ID, returnMessage);
            }*/
        } catch (Exception e) {
            logger.error("exception:", e);
            map.put("errorCode", -1);
            map.put("msg", "异常:" + e.getMessage());
        }
        return map;
    }


    public static String doGet(String url) {
        try {
            HttpClient client = new DefaultHttpClient();
            //发送get请求
            HttpGet request = new HttpGet(url);
            HttpResponse response = client.execute(request);

            /**请求发送成功，并得到响应**/
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                /**读取服务器返回过来的json字符串数据**/
                String strResult = EntityUtils.toString(response.getEntity());

                return strResult;
            }
        }
        catch (IOException e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }

        return null;
    }


/*    public static void main(String[] args) {
        RegisterController rc=new RegisterController();
        User user=new User();
        user.setPassword("a1234567");
        String url="http://localhost:8080/proxy/reg/createUser?password=a1234567&smsCode=0000&phoneNumber=137222222";
        for(int i=10;i<60;i++){
            //user.setPhoneNumber("137222222"+i);
            //rc.createUser(user,"0000",null,null,null,null,null);
            url=url+i;
            doGet(url);
        }
    }*/

}
