package com.common.utils;

import com.common.cache.CacheClient;
import com.common.entity.Constants;
import com.pojo.User;
import com.pojo.UserToken;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Random;

/**
 * 获取当前用户登录态信息
 * 
 * @author Administrator
 * 
 */
public class UserInfoUtil {

	private CacheClient cacheClient;

	public UserToken getUserToken(Integer i,HttpServletRequest request) {
		UserToken uk=new UserToken();
		uk.setUserId(237);
		return uk;
	}


		public UserToken getUserToken(HttpServletRequest request) {
		String userName = null;
		String utoken = null;
		String signType = null;

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
				if (cookie.getName().equals("signType")) {
					signType = cookie.getValue();
				}
			}
		}

		if (userName == null || utoken == null) {
			// 支持非cookie的情况, 认证成功回调，则会奖userName,utoken在httpget参数中带过来
			userName = request.getParameter("userName");
			utoken = request.getParameter("utoken");
			signType = request.getParameter("signType");
		}

		if (userName == null || "".equals(userName) || utoken == null || "".equals(utoken)) {
			return null;
		} else {
			String md5Key = MD5Util.md5(userName + "#" + utoken );
			// cache中校验登录态是否有效
			try {
				String TOKEN_KEY = Constants.FORUM_U_TOKEN_KEY + md5Key;
				Object obj = cacheClient.get(TOKEN_KEY);
				if (obj != null) {
					UserToken userToken = (UserToken) obj;
					return userToken;
				} else {
					return null;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
	}

	public CacheClient getCacheClient() {
		return cacheClient;
	}

	public void setCacheClient(CacheClient cacheClient) {
		this.cacheClient = cacheClient;
	}

	public  static  String generateInviteCode(int lenth){
		Random ra =new Random();
		String res="";
		for(int i=0;i<lenth;i++){
			int r=ra.nextInt(9) + 0;
			res+=r;
		}
		return res;
	}
	public static User concealUserInfo(User user){
		user.setPassword(null);
		user.setPhoneNumber(null);
		user.setUserName(null);
		return user;
	}

	public static List<User> concealUserInfo(List<User> users){
		for(User user:users){
			concealUserInfo(user);
		}
			return users;
	}


}
