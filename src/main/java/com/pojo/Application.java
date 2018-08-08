package com.pojo;

import java.sql.Timestamp;

public class Application {
    private Integer applicationId;
    private Timestamp applicationTime;
    private Integer userId;
    private Integer auditorId;
    private Timestamp auditTime;
    private String alipayAccount;
    private String alipayName;
    private Integer goldNum;
    private Integer applyState;
    private String refuseReason;
    private String phoneNumber;

    private Timestamp createTime;
    private Timestamp lastUpdateTime;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    public String getRefuseReason() {
        return refuseReason;
    }

    public void setRefuseReason(String refuseReason) {
        this.refuseReason = refuseReason;
    }

    public Integer getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Integer applicationId) {
        this.applicationId = applicationId;
    }

    public Timestamp getApplicationTime() {
        return applicationTime;
    }

    public void setApplicationTime(Timestamp applicationTime) {
        this.applicationTime = applicationTime;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getAuditorId() {
        return auditorId;
    }

    public void setAuditorId(Integer auditorId) {
        this.auditorId = auditorId;
    }

    public Timestamp getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Timestamp auditTime) {
        this.auditTime = auditTime;
    }

    public String getAlipayAccount() {
        return alipayAccount;
    }

    public void setAlipayAccount(String alipayAccount) {
        this.alipayAccount = alipayAccount;
    }

    public String getAlipayName() {
        return alipayName;
    }

    public void setAlipayName(String alipayName) {
        this.alipayName = alipayName;
    }

    public Integer getGoldNum() {
        return goldNum;
    }

    public void setGoldNum(Integer goldNum) {
        this.goldNum = goldNum;
    }

    public Integer getApplyState() {
        return applyState;
    }

    public void setApplyState(Integer applyState) {
        this.applyState = applyState;
    }

    @Override
    public String toString() {
        return "Application{" +
                "applicationId=" + applicationId +
                ", applicationTime=" + applicationTime +
                ", userId=" + userId +
                ", auditorId=" + auditorId +
                ", auditTime=" + auditTime +
                ", alipayAccount='" + alipayAccount + '\'' +
                ", alipayName='" + alipayName + '\'' +
                ", goldNum=" + goldNum +
                ", applyState=" + applyState +
                ", refuseReason='" + refuseReason + '\'' +
                ", createTime=" + createTime +
                ", lastUpdateTime=" + lastUpdateTime +
                '}';
    }
}
