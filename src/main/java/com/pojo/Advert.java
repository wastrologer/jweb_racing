package com.pojo;

import java.util.Date;

public class Advert {
    private Long advertId;

    private String customerName;

    private String advertTitle;

    private String advertImage;

    private String advertUrl;

    private Date onlineTime;

    private Date downlineTime;

    private Integer advertPosition;

    private Long browseNum;

    private Long clickNum;

    private Integer state;

    private Integer isDel;

    private Date createTime;

    private Date lastupdatetime;

    @Override
    public String toString() {
        return "Advert{" +
                "advertId=" + advertId +
                ", customerName='" + customerName + '\'' +
                ", advertTitle='" + advertTitle + '\'' +
                ", advertImage='" + advertImage + '\'' +
                ", advertUrl='" + advertUrl + '\'' +
                ", onlineTime=" + onlineTime +
                ", downlineTime=" + downlineTime +
                ", advertPosition=" + advertPosition +
                ", browseNum=" + browseNum +
                ", clickNum=" + clickNum +
                ", state=" + state +
                ", isDel=" + isDel +
                ", createTime=" + createTime +
                ", lastupdatetime=" + lastupdatetime +
                '}';
    }

    public Long getAdvertId() {
        return advertId;
    }

    public void setAdvertId(Long advertId) {
        this.advertId = advertId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName == null ? null : customerName.trim();
    }

    public String getAdvertTitle() {
        return advertTitle;
    }

    public void setAdvertTitle(String advertTitle) {
        this.advertTitle = advertTitle == null ? null : advertTitle.trim();
    }

    public String getAdvertImage() {
        return advertImage;
    }

    public void setAdvertImage(String advertImage) {
        this.advertImage = advertImage == null ? null : advertImage.trim();
    }

    public String getAdvertUrl() {
        return advertUrl;
    }

    public void setAdvertUrl(String advertUrl) {
        this.advertUrl = advertUrl == null ? null : advertUrl.trim();
    }

    public Date getOnlineTime() {
        return onlineTime;
    }

    public void setOnlineTime(Date onlineTime) {
        this.onlineTime = onlineTime;
    }

    public Date getDownlineTime() {
        return downlineTime;
    }

    public void setDownlineTime(Date downlineTime) {
        this.downlineTime = downlineTime;
    }

    public Integer getAdvertPosition() {
        return advertPosition;
    }

    public void setAdvertPosition(Integer advertPosition) {
        this.advertPosition = advertPosition;
    }

    public Long getBrowseNum() {
        return browseNum;
    }

    public void setBrowseNum(Long browseNum) {
        this.browseNum = browseNum;
    }

    public Long getClickNum() {
        return clickNum;
    }

    public void setClickNum(Long clickNum) {
        this.clickNum = clickNum;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastupdatetime() {
        return lastupdatetime;
    }

    public void setLastupdatetime(Date lastupdatetime) {
        this.lastupdatetime = lastupdatetime;
    }
}