package com.pojo;

import java.sql.Timestamp;
import java.util.Date;

/**
 * 存放用户登陆态
 * @author admin
 *
 */
public class UserToken implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	private String token;//用户登陆token
	private long userId;
	private String signType;//认证类型，WEB认证；APP认证
	private String authSeq;//认证流水记录，暂时不用，留有后面扩展使用
	private Date authTime;//认证时间
	private Date expireTime;//过期时间
	private Long lastRefreshTime;//最后刷新时间
	private Timestamp createTime;
	private Timestamp lastUpdateTime;

	@Override
	public String toString() {
		return "UserToken{" +
				"token='" + token + '\'' +
				", userId=" + userId +
				", signType='" + signType + '\'' +
				", authSeq='" + authSeq + '\'' +
				", authTime=" + authTime +
				", expireTime=" + expireTime +
				", lastRefreshTime=" + lastRefreshTime +
				", createTime=" + createTime +
				", lastUpdateTime=" + lastUpdateTime +
				'}';
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Timestamp lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public Long getLastRefreshTime() {
		return lastRefreshTime;
	}

	public void setLastRefreshTime(Long lastRefreshTime) {
		this.lastRefreshTime = lastRefreshTime;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	public String getAuthSeq() {
		return authSeq;
	}

	public void setAuthSeq(String authSeq) {
		this.authSeq = authSeq;
	}

	public Date getAuthTime() {
		return authTime;
	}

	public void setAuthTime(Date authTime) {
		this.authTime = authTime;
	}

	public Date getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}
}
