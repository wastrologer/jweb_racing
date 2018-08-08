package com.pojo;


import java.sql.Timestamp;

public class Coin {
    private Integer coinRecordId;
    private Timestamp coinRecordTime;
    private Integer coinNum;
    private Integer coinUserId;
    private String coinReason;
    private Timestamp createTime;
    private Timestamp lastUpdateTime;

    @Override
    public String toString() {
        return "Coin{" +
                "coinRecordId=" + coinRecordId +
                ", coinRecordTime=" + coinRecordTime +
                ", coinNum=" + coinNum +
                ", coinUserId=" + coinUserId +
                ", coinReason='" + coinReason + '\'' +
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

    public String getCoinReason() {
        return coinReason;
    }

    public void setCoinReason(String coinReason) {
        this.coinReason = coinReason;
    }

    public Integer getCoinRecordId() {
        return coinRecordId;
    }

    public void setCoinRecordId(Integer coinRecordId) {
        this.coinRecordId = coinRecordId;
    }

    public Timestamp getCoinRecordTime() {
        return coinRecordTime;
    }

    public void setCoinRecordTime(Timestamp coinRecordTime) {
        this.coinRecordTime = coinRecordTime;
    }

    public Integer getCoinNum() {
        return coinNum;
    }

    public void setCoinNum(Integer coinNum) {
        this.coinNum = coinNum;
    }

    public Integer getCoinUserId() {
        return coinUserId;
    }

    public void setCoinUserId(Integer coinUserId) {
        this.coinUserId = coinUserId;
    }

    public Coin(Timestamp coinRecordTime, Integer coinNum, Integer coinUserId, String coinReason) {
        this.coinRecordTime = coinRecordTime;
        this.coinNum = coinNum;
        this.coinUserId = coinUserId;
        this.coinReason = coinReason;
    }

    public Coin() {
    }
}
