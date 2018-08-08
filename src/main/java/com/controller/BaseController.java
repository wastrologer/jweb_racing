package com.controller;

import com.common.utils.UserInfoUtil;
import com.constant.ErrorCode;
import com.github.pagehelper.PageInfo;
import com.pojo.UserToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: gravity Date: 15-7-8 Time: 上午9:57
 */
@Component
public class BaseController {

	protected static final Logger logger = LoggerFactory.getLogger(BaseController.class);

	protected HttpServletRequest request;

	protected HttpServletResponse response;

	
	@Autowired
	protected UserInfoUtil userInfoUtil;

	@ModelAttribute
	public void setReqAndRes(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;

	}

	public String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		logger.info("x-forwarded-for, ip=" + ip);
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
			logger.info("Proxy-Client-IP, ip=" + ip);
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
			logger.info("WL-Proxy-Client-IP, ip=" + ip);
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
			logger.info("getRemoteAddr, ip=" + ip);
		}
		return ip;
	}
	
	public String getNewIpAddr(HttpServletRequest request) {
		String ipAddress = request.getHeader("x-forwarded-for");
		logger.info("getNewIpAddr x-forwarded-for, ip=" + ipAddress);
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getRemoteAddr();
		}
		// 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
		if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length()
			if (ipAddress.indexOf(",") > 0) {
				ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
			}
		}
		return ipAddress;
	}
	
	public UserToken getUserToken() {
		UserToken userToken = userInfoUtil.getUserToken(request);
		if ("1".equals(System.getenv("junitTest")) || "1".equals(System.getProperty("junitTest"))) {
			userToken = new UserToken();
			userToken.setUserId(2);
		}
		return userToken;
	}

	public UserToken getUserTokenTest() {
		UserToken userToken = userInfoUtil.getUserToken(1,request);
		return userToken;
	}

	public Map<String, Object> getSuccessMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("errorCode", ErrorCode.OPERATE_SUCCESS_CODE);
		map.put("data", null);
		map.put("msg", "成功");
		return map;
	}

	public Map<String, Object> getObjsMap(Object... objs) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("errorCode", ErrorCode.OPERATE_SUCCESS_CODE);
		for(Object obj:objs){
			map.put(obj.getClass().getSimpleName(), obj);
		}
		map.put("msg", "成功");
		return map;
	}
	public Map<String, Object> getStrMap(PageInfo pageInfo) {
		return getStrMap(pageInfo.getList(), (int) pageInfo.getTotal());
	}

		public Map<String, Object> getStrMap(List list,Integer totalAccount) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> juniorMap = new HashMap<String, Object>();
		map.put("errorCode", ErrorCode.OPERATE_SUCCESS_CODE);
		juniorMap.put("list", list);
		juniorMap.put("totalAccount",totalAccount);
		map.put("data", juniorMap);
		map.put("msg", "成功");
		return map;
	}
	public Map<String, Object> getStrMap(String str,Object obj ) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> juniorMap = new HashMap<String, Object>();
		map.put("errorCode", ErrorCode.OPERATE_SUCCESS_CODE);
		juniorMap.put(str, obj);
		map.put("data", juniorMap);
		map.put("msg", "成功");
		return map;
	}
	public Map<String, Object> getStrMap(List list,Integer totalAccount,String str1,Object obj1) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> juniorMap = new HashMap<String, Object>();
		map.put("errorCode", ErrorCode.OPERATE_SUCCESS_CODE);
		juniorMap.put("list", list);
		juniorMap.put("totalAccount", totalAccount);
		juniorMap.put(str1, obj1);
		map.put("data", juniorMap);
		map.put("msg", "成功");
		return map;
	}

	public Map<String, Object> getStrMap(String str,Object obj,String str1,Object obj1) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> juniorMap = new HashMap<String, Object>();
		map.put("errorCode", ErrorCode.OPERATE_SUCCESS_CODE);
		juniorMap.put(str,obj);
		juniorMap.put(str1, obj1);
		map.put("data", juniorMap);
		map.put("msg", "成功");
		return map;
	}
	public Map<String, Object> getStrMap(List list,Integer totalAccount,String str,Object obj,String str1,Object obj1) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> juniorMap = new HashMap<String, Object>();
		map.put("errorCode", ErrorCode.OPERATE_SUCCESS_CODE);
		juniorMap.put("list", list);
		juniorMap.put("totalAccount",totalAccount);
		juniorMap.put(str,obj);
		juniorMap.put(str1, obj1);
		map.put("data", juniorMap);
		map.put("msg", "成功");
		return map;
	}

    public Map<String, Object> getStrMap(List list,Integer totalAccount,String str,Object obj,String str1,Object obj1,String str11,Object obj11) {
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> juniorMap = new HashMap<String, Object>();
        map.put("errorCode", ErrorCode.OPERATE_SUCCESS_CODE);
        juniorMap.put("list", list);
        juniorMap.put("totalAccount",totalAccount);
        juniorMap.put(str,obj);
        juniorMap.put(str1, obj1);
        juniorMap.put(str11, obj11);
        map.put("data", juniorMap);
        map.put("msg", "成功");
        return map;
    }

	public Map<String, Object> getStrMap(String str,Object obj,String str1,Object obj1,String str2,Object obj2) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> juniorMap = new HashMap<String, Object>();
		map.put("errorCode", ErrorCode.OPERATE_SUCCESS_CODE);
		juniorMap.put(str,obj);
		juniorMap.put(str1, obj1);
		juniorMap.put(str2, obj2);
		map.put("data", juniorMap);
		map.put("msg", "成功");
		return map;
	}

	public Map<String, Object> getErrorMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("errorCode", ErrorCode.STANDARD_ERROR_CODE);
		map.put("data", null);
		map.put("msg", "操作失败");
		logger.warn("操作失败",map);
		return map;
	}

	public Map<String, Object> getErrorMap(Integer errorCode,String s) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("errorCode", errorCode);
		map.put("data", null);
		map.put("msg", s);
		logger.warn(s,map);
		return map;
	}

	public Map<String, Object> getErrorMap(String s,Object obj) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("errorCode", ErrorCode.STANDARD_ERROR_CODE);
		map.put("data", obj);
		map.put("msg", s);
		logger.warn(s+obj,map);
		return map;
	}


	public Map<String, Object> getErrorMap(Object s) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("errorCode", ErrorCode.STANDARD_ERROR_CODE);
		map.put("data", s);
		map.put("msg", "操作失败");
		logger.warn("操作失败"+s,map);
		return map;
	}

	public Map<String, Object> getUnlogedErrorMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("errorCode", ErrorCode.USER_LOGIN_ERROR_UNLOGIN);
		map.put("data", null);
		map.put("msg", "用户未登录");
		logger.warn("用户未登录或拿不到登录状态",map);
		return map;
	}

}
