package com.pojo;

import java.sql.Timestamp;


public class Gold {
    private Integer goldRecordId;
    private Timestamp goldRecordTime;
    private Integer goldNum;
    private Integer goldUserId;
    private String goldReason;
    private Integer waterType;
    private Long retId;
    private Timestamp createTime;
    private Timestamp lastUpdateTime;

    @Override
    public String toString() {
        return "Gold{" +
                "goldRecordId=" + goldRecordId +
                ", goldRecordTime=" + goldRecordTime +
                ", goldNum=" + goldNum +
                ", goldUserId=" + goldUserId +
                ", goldReason='" + goldReason + '\'' +
                ", waterType=" + waterType +
                ", retId=" + retId +
                ", createTime=" + createTime +
                ", lastUpdateTime=" + lastUpdateTime +
                '}';
    }

    public Integer getWaterType() {
        return waterType;
    }

    public void setWaterType(Integer waterType) {
        this.waterType = waterType;
    }

    public Long getRetId() {
        return retId;
    }

    public void setRetId(Long retId) {
        this.retId = retId;
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

    public Integer getGoldRecordId() {
        return goldRecordId;
    }

    public void setGoldRecordId(Integer goldRecordId) {
        this.goldRecordId = goldRecordId;
    }

    public Timestamp getGoldRecordTime() {
        return goldRecordTime;
    }

    public void setGoldRecordTime(Timestamp goldRecordTime) {
        this.goldRecordTime = goldRecordTime;
    }

    public Integer getGoldNum() {
        return goldNum;
    }

    public void setGoldNum(Integer goldNum) {
        this.goldNum = goldNum;
    }

    public Integer getGoldUserId() {
        return goldUserId;
    }

    public void setGoldUserId(Integer goldUserId) {
        this.goldUserId = goldUserId;
    }

    public String getGoldReason() {
        return goldReason;
    }

    public void setGoldReason(String goldReason) {
        this.goldReason = goldReason;
    }

    public Gold() {
    }

    public Gold(Timestamp goldRecordTime, Integer goldNum, Integer goldUserId, String goldReason) {
        this.goldRecordTime = goldRecordTime;
        this.goldNum = goldNum;
        this.goldUserId = goldUserId;
        this.goldReason = goldReason;
    }
}
