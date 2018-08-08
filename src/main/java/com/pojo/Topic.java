package com.pojo;

import java.sql.Timestamp;

public class Topic {
    private  Integer topicId;
    private String topicName;
    private String topicPic;
    private Integer commentNum;
    private Timestamp createTime;
    private Timestamp lastUpdateTime;
    private Integer isShow;

    private Long partakeNum;

    @Override
    public String toString() {
        return "Topic{" +
                "topicId=" + topicId +
                ", topicName='" + topicName + '\'' +
                ", topicPic='" + topicPic + '\'' +
                ", commentNum=" + commentNum +
                ", createTime=" + createTime +
                ", lastUpdateTime=" + lastUpdateTime +
                ", isShow=" + isShow +
                ", partakeNum=" + partakeNum +
                '}';
    }

    public Integer getIsShow() {
        return isShow;
    }

    public void setIsShow(Integer isShow) {
        this.isShow = isShow;
    }

    public Long getPartakeNum() {
        return partakeNum;
    }

    public void setPartakeNum(Long partakeNum) {
        this.partakeNum = partakeNum;
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

    public Integer getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(Integer commentNum) {
        this.commentNum = commentNum;
    }

    public Integer getTopicId() {
        return topicId;
    }

    public void setTopicId(Integer topicId) {
        this.topicId = topicId;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getTopicPic() {
        return topicPic;
    }

    public void setTopicPic(String topicPic) {
        this.topicPic = topicPic;
    }
}
