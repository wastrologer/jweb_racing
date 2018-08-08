package com.pojo;

import java.sql.Timestamp;

public class Collection {
    private Integer collectionId;
    private Timestamp collectionTime;
    private Integer essayId;
    private Integer userId;
    private String orderBy;
    private Integer isPublished;//可不填，限制数字范围，是否发布，默认0，0草稿，1发布
    private Integer isHiden=1;//可不填，限制数字范围，1不隐藏 2隐藏

    private Timestamp createTime;
    private Timestamp lastUpdateTime;

    @Override
    public String toString() {
        return "Collection{" +
                "collectionId=" + collectionId +
                ", collectionTime=" + collectionTime +
                ", essayId=" + essayId +
                ", userId=" + userId +
                ", orderBy='" + orderBy + '\'' +
                ", isPublished=" + isPublished +
                ", isHiden=" + isHiden +
                ", createTime=" + createTime +
                ", lastUpdateTime=" + lastUpdateTime +
                '}';
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


    public Integer getIsPublished() {
        return isPublished;
    }

    public void setIsPublished(Integer isPublished) {
        this.isPublished = isPublished;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public Integer getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(Integer collectionId) {
        this.collectionId = collectionId;
    }

    public Timestamp getCollectionTime() {
        return collectionTime;
    }

    public void setCollectionTime(Timestamp collectionTime) {
        this.collectionTime = collectionTime;
    }

    public Integer getEssayId() {
        return essayId;
    }

    public void setEssayId(Integer essayId) {
        this.essayId = essayId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Collection(Timestamp collectionTime, Integer essayId, Integer userId) {
        this.collectionTime = collectionTime;
        this.essayId = essayId;
        this.userId = userId;
        
    }

    public Collection() {
    }
}
