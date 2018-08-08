package com.pojo;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class EssayParam {
    private  boolean isHot;
    private Integer isPublished;//0否
    private List<Integer> exceptionIdList;
    private List<Integer> userIdList;
    private List<Integer> regionIdList;
    private List<Integer> essayTypeList;
    private Integer cityId;//1深圳，2广州
    private Integer regionId;
    private Integer essayType;
    private String essayTitle;
    private String essayContent;
    private Integer topicId;
    private List<Integer> topicIdList;

    private Integer recommendNumFrom;
    private Integer commentNumFrom;
    private Integer clickNumFrom;
    private Date publishTimeFrom;
    private Date createTimeFrom;
    private Integer recommendNumTo;
    private Integer commentNumTo;
    private Integer clickNumTo;
    private Date publishTimeTo;
    private Date createTimeTo;
    private String orderBy;
    private List<String> keywords;
    private Timestamp createTime;
    private Timestamp lastUpdateTime;
    private Integer isHiden=1;//不填，自动生成，1不隐藏，2隐藏

    @Override
    public String toString() {
        return "EssayParam{" +
                "isHot=" + isHot +
                ", isPublished=" + isPublished +
                ", exceptionIdList=" + exceptionIdList +
                ", userIdList=" + userIdList +
                ", regionIdList=" + regionIdList +
                ", essayTypeList=" + essayTypeList +
                ", regionId=" + regionId +
                ", essayType=" + essayType +
                ", essayTitle='" + essayTitle + '\'' +
                ", essayContent='" + essayContent + '\'' +
                ", topicId=" + topicId +
                ", topicIdList=" + topicIdList +
                ", cityId=" + cityId +
                ", recommendNumFrom=" + recommendNumFrom +
                ", commentNumFrom=" + commentNumFrom +
                ", clickNumFrom=" + clickNumFrom +
                ", publishTimeFrom=" + publishTimeFrom +
                ", createTimeFrom=" + createTimeFrom +
                ", recommendNumTo=" + recommendNumTo +
                ", commentNumTo=" + commentNumTo +
                ", clickNumTo=" + clickNumTo +
                ", publishTimeTo=" + publishTimeTo +
                ", createTimeTo=" + createTimeTo +
                ", orderBy='" + orderBy + '\'' +
                ", keywords=" + keywords +
                ", createTime=" + createTime +
                ", lastUpdateTime=" + lastUpdateTime +
                ", isHiden=" + isHiden +
                '}';
    }


    public Integer getRegionId() {
        return regionId;
    }

    public void setRegionId(Integer regionId) {
        this.regionId = regionId;
    }

    public Integer getEssayType() {
        return essayType;
    }

    public void setEssayType(Integer essayType) {
        this.essayType = essayType;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public List<Integer> getTopicIdList() {
        return topicIdList;
    }

    public void setTopicIdList(List<Integer> topicIdList) {
        this.topicIdList = topicIdList;
    }

    public Integer getIsHiden() {
        return isHiden;
    }

    public void setIsHiden(Integer isHiden) {
        this.isHiden = isHiden;
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

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public boolean getIsHot() {
        return isHot;
    }

    public void setIsHot(boolean hot) {
        isHot = hot;
    }

    public Date getCreateTimeFrom() {
        return createTimeFrom;
    }

    public void setCreateTimeFrom(Date createTimeFrom) {
        this.createTimeFrom = createTimeFrom;
    }

    public Date getCreateTimeTo() {
        return createTimeTo;
    }

    public void setCreateTimeTo(Date createTimeTo) {
        this.createTimeTo = createTimeTo;
    }

    public Boolean getHot() {
        return isHot;
    }

    public void setHot(Boolean hot) {
        isHot = hot;
    }

    public Integer getIsPublished() {
        return isPublished;
    }

    public void setIsPublished(Integer isPublished) {
        this.isPublished = isPublished;
    }

    public List<Integer> getExceptionIdList() {
        return exceptionIdList;
    }

    public void setExceptionIdList(List<Integer> exceptionIdList) {
        this.exceptionIdList = exceptionIdList;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public List<Integer> getRegionIdList() {
        return regionIdList;
    }

    public void setRegionIdList(List<Integer> regionIdList) {
        this.regionIdList = regionIdList;
    }

    public List<Integer> getUserIdList() {
        return userIdList;
    }

    public void setUserIdList(List<Integer> userIdList) {
        this.userIdList = userIdList;
    }

    public String getEssayTitle() {
        return essayTitle;
    }

    public void setEssayTitle(String essayTitle) {
        this.essayTitle = essayTitle;
    }

    public String getEssayContent() {
        return essayContent;
    }

    public void setEssayContent(String essayContent) {
        this.essayContent = essayContent;
    }

    public List<Integer> getEssayTypeList() {
        return essayTypeList;
    }

    public void setEssayTypeList(List<Integer> essayTypeList) {
        this.essayTypeList = essayTypeList;
    }

    public Integer getTopicId() {
        return topicId;
    }

    public void setTopicId(Integer topicId) {
        this.topicId = topicId;
    }

    public Integer getRecommendNumFrom() {
        return recommendNumFrom;
    }

    public void setRecommendNumFrom(Integer recommendNumFrom) {
        this.recommendNumFrom = recommendNumFrom;
    }

    public Integer getCommentNumFrom() {
        return commentNumFrom;
    }

    public void setCommentNumFrom(Integer commentNumFrom) {
        this.commentNumFrom = commentNumFrom;
    }

    public Integer getClickNumFrom() {
        return clickNumFrom;
    }

    public void setClickNumFrom(Integer clickNumFrom) {
        this.clickNumFrom = clickNumFrom;
    }

    public Date getPublishTimeFrom() {
        return publishTimeFrom;
    }

    public void setPublishTimeFrom(Date publishTimeFrom) {
        this.publishTimeFrom = publishTimeFrom;
    }

    public Integer getRecommendNumTo() {
        return recommendNumTo;
    }

    public void setRecommendNumTo(Integer recommendNumTo) {
        this.recommendNumTo = recommendNumTo;
    }

    public Integer getCommentNumTo() {
        return commentNumTo;
    }

    public void setCommentNumTo(Integer commentNumTo) {
        this.commentNumTo = commentNumTo;
    }

    public Integer getClickNumTo() {
        return clickNumTo;
    }

    public void setClickNumTo(Integer clickNumTo) {
        this.clickNumTo = clickNumTo;
    }

    public Date getPublishTimeTo() {
        return publishTimeTo;
    }

    public void setPublishTimeTo(Date publishTimeTo) {
        this.publishTimeTo = publishTimeTo;
    }
}
