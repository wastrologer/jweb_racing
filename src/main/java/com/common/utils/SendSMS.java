package com.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.common.cache.CacheClient;
import com.common.entity.Constants;
import com.common.entity.SMSCountVo;
import com.common.entity.SMSVo;
import com.constant.ErrorCode;
import net.rubyeye.xmemcached.GetsResponse;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Random;

public class SendSMS {
	private static final String encode = "utf8";
	private static final String accountSid = "f657a3aaf16b159b1819de53b8b0260b";
	private static final String authToken = "07f80e4367d588ad084d95721ecd12b6";
	private static final String restURL = "https://api.ucpaas.com/2014-06-30/Accounts/f657a3aaf16b159b1819de53b8b0260b/Messages/templateSMS";
	private static final String appID = "ed83e5892df6479a9c453461abb09e72";

/*	AppID：ed83e5892df6479a9c453461abb09e72
	Account Sid：f657a3aaf16b159b1819de53b8b0260b
	Auth Token：07f80e4367d588ad084d95721ecd12b6
	Rest URL：https://open.ucpaas.com/ol/sms/{function}*/

	/*AppID（应用ID）：c719ae524dc14097a202e69f36dc27ad
Account Sid（用户sid）：e566d84ae1f76d24e6b8e3a9ee1cd23c
Auth Token（鉴权密钥）：a2e5d79cf40def92307ba2c154eb99ef
Rest URL（请求地址）：https://open.ucpaas.com/ol/sms/{function}*/
											
	protected static final Logger logger = LoggerFactory.getLogger(SendSMS.class);

	// 随机4位数字验证码
	public static String generateValiateCode() {
		Random rnd = new Random();
		int n = 1000 + rnd.nextInt(9000);
		return String.valueOf(n);
	}

	public static void sendUCP(String telphone, Map<String, Object> map, String smsCountKey, String smsKey,
                               String[] content, CacheClient cacheClient, Integer templateId) {
		try {
			GetsResponse<Object> smsCountVoGetsResponse = cacheClient.gets(smsCountKey);
			SMSCountVo smsCountVo;
			String contentStr = getContentLinked(content);
			if (smsCountVoGetsResponse == null) {
				SMSVo smsVo = new SMSVo();
				smsVo.setContent(contentStr);
				smsVo.setTimestamp(HelperUtil.getSecondStamp());
				smsCountVo = new SMSCountVo();
				smsCountVo.setCount(1);
				smsCountVo.setTimestamp(HelperUtil.getSecondStamp());
				SendSMS.sendUCPOld(telphone, content, templateId);
				cacheClient.set(smsKey, Constants.SMS_SEND_EXPIRE_SECOND, smsVo);
				cacheClient.set(smsCountKey, Constants.SMS_COUNT_EXPIRE_SECOND, smsCountVo);
			} else {
				smsCountVo = (SMSCountVo) smsCountVoGetsResponse.getValue();
				if (smsCountVo.getCount() < Constants.DEFAULT_DAY_MAX_SMS_COUNT) {
					SMSVo smsVo = (SMSVo) cacheClient.get(smsKey);
					if (smsVo == null) {
						smsVo = new SMSVo();
						smsVo.setTimestamp(HelperUtil.getSecondStamp());
						smsVo.setContent(contentStr);
					} else {
						smsVo.setContent(contentStr);
						smsVo.setTimestamp(HelperUtil.getSecondStamp());
					}
					smsCountVo.setCount(smsCountVo.getCount() + 1);
					SendSMS.sendUCPOld(telphone, content, templateId);
					cacheClient.set(smsKey, Constants.SMS_SEND_EXPIRE_SECOND, smsVo);
					cacheClient.cas(smsCountKey,
							(int) ((24 * 3600) - (HelperUtil.getSecondStamp() - smsCountVo.getTimestamp())),
							smsCountVo, smsCountVoGetsResponse.getCas());
				} else {
					logger.info("用户发送短信超过最大条数");
					map.put("errorCode", ErrorCode.USER_SEND_SMS_EXCEED_COUNT);
					map.put("msg", "用户发送短信超过最大条数");
				}
			}
		} catch (Exception e) {
			logger.error("发送短信发生异常：", e);
		}
	}

	// 发送短信
	public static void sendUCPOld(String mobile, String[] content, Integer templateId) {
		int status = 0;
		DateFormat formater = new SimpleDateFormat("yyyyMMddHHmmss");
		String timestamp = formater.format(new Date());

		HttpClient client = new HttpClient();
		PostMethod method = new PostMethod(restURL + "?sig=" + getUcpSigParameter(timestamp));
		method.addRequestHeader("Content-Type", "application/json;charset=utf-8");// 在头文件中设置转码
		method.addRequestHeader("Accept", "application/json");// 在头文件中设置转码
		method.addRequestHeader("Authorization", getUcpAuthorization(timestamp));// 在头文件中设置转码
		method.getParams().setContentCharset(encode);
		HttpConnectionManagerParams managerParams = client.getHttpConnectionManager().getParams();

		try {
			JSONObject templateSMS = new JSONObject();
			templateSMS.put("appId", appID);
			templateSMS.put("param", getContentLinked(content));
			templateSMS.put("templateId", templateId + "");
			templateSMS.put("to", mobile);

			JSONObject jsonObject = new JSONObject();
			jsonObject.put("templateSMS", templateSMS);

			RequestEntity entity = new StringRequestEntity(jsonObject.toJSONString(), null, "utf-8");

			method.setRequestEntity(entity);
			method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(3, true));
			managerParams.setConnectionTimeout(10000);
			managerParams.setSoTimeout(10000);
			logger.info("templateID:" + templateId + ", telphone : " + mobile + ", content : "
					+ getContentLinked(content));
			status = client.executeMethod(method);
			int count = 0;
			while (status != HttpStatus.SC_OK && count < 3) {
				status = client.executeMethod(method);
				count++;
			}
			System.out.println(method.getResponseBodyAsString());
			logger.info("短信发送状态码：" + method.getResponseBodyAsString());
		} catch (Exception e) {
			logger.error("exception", e);
		} finally {
			if (method != null)
				method.releaseConnection();
			if (client != null) {
				try {
					((SimpleHttpConnectionManager) client.getHttpConnectionManager()).closeIdleConnections(0);
				} catch (Exception e) {
					logger.error("exception", e);
				}
				client = null;
			}
		}
	}

	private static String getUcpAuthorization(String timeStamp) {
		return Base64Util.getBase64(accountSid + ":" + timeStamp);
	}

	private static String getUcpSigParameter(String timeStamp) {
		return MD5Util.md5(accountSid + authToken + timeStamp).toUpperCase();
	}

	private static String getContentLinked(String[] content) {
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < content.length; i++) {
			result.append(content[i]);
			if (i != content.length - 1)
				result.append(",");
		}
		return result.toString();
	}

}
