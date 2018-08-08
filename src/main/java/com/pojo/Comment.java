package com.pojo;


import java.sql.Timestamp;
import java.util.List;

public class Comment {
    private Integer commentId;//不填，自动生成，评论ID
    private Integer userId;//不填，自动生成，发布用户ID
    private String userNickname;//不填，自动生成，发布用户昵称
    private Integer essayId;//必填，文章ID
    private String commentContent;//必填，限制长度，内容
    private Integer commentGrade;//必填，限制数字范围，评论等级，1一级评论，2次级评论（对评论发表的评论）
    private Integer commentSequence;//不填，暂时不用
    private Integer seniorCommentId;//一级评论不填，次级评论必填，代表高级评论的ID
    private Timestamp publishTime;//不填，自动生成，发布时间
    private Integer topicId;//不填，自动生成，主题ID
    private List<Comment> juniorComments;
    private Boolean    recommended;//是否点赞
    private String     userPic;//用户头像
    private Integer     recommendNum;
    private Timestamp createTime;
    private Timestamp lastUpdateTime;
    private Integer     isDel;//1是2否
    private String authId;//作者auth

    @Override
    public String toString() {
        return "Comment{" +
                "commentId=" + commentId +
                ", userId=" + userId +
                ", userNickname='" + userNickname + '\'' +
                ", essayId=" + essayId +
                ", commentContent='" + commentContent + '\'' +
                ", commentGrade=" + commentGrade +
                ", commentSequence=" + commentSequence +
                ", seniorCommentId=" + seniorCommentId +
                ", publishTime=" + publishTime +
                ", topicId=" + topicId +
                ", juniorComments=" + juniorComments +
                ", recommended=" + recommended +
                ", userPic='" + userPic + '\'' +
                ", recommendNum=" + recommendNum +
                ", createTime=" + createTime +
                ", lastUpdateTime=" + lastUpdateTime +
                ", isDel=" + isDel +
                '}';
    }

    public String getAuthId() {
        return authId;
    }

    public void setAuthId(String authId) {
        this.authId = authId;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public Boolean getRecommended() {
        return recommended;
    }

    public void setRecommended(Boolean recommended) {
        this.recommended = recommended;
    }

    public String getUserPic() {
        return userPic;
    }

    public void setUserPic(String userPic) {
        this.userPic = userPic;
    }

    public Integer getRecommendNum() {
        return recommendNum;
    }

    public void setRecommendNum(Integer recommendNum) {
        this.recommendNum = recommendNum;
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

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public List<Comment> getJuniorComments() {
        return juniorComments;
    }

    public void setJuniorComments(List<Comment> juniorComments) {
        this.juniorComments = juniorComments;
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getEssayId() {
        return essayId;
    }

    public void setEssayId(Integer essayId) {
        this.essayId = essayId;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public Integer getCommentGrade() {
        return commentGrade;
    }

    public void setCommentGrade(Integer commentGrade) {
        this.commentGrade = commentGrade;
    }

    public Integer getCommentSequence() {
        return commentSequence;
    }

    public void setCommentSequence(Integer commentSequence) {
        this.commentSequence = commentSequence;
    }

    public Integer getSeniorCommentId() {
        return seniorCommentId;
    }

    public void setSeniorCommentId(Integer seniorCommentId) {
        this.seniorCommentId = seniorCommentId;
    }

    public Timestamp getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Timestamp publishTime) {
        this.publishTime = publishTime;
    }

    public Integer getTopicId() {
        return topicId;
    }

    public void setTopicId(Integer topicId) {
        this.topicId = topicId;
    }
}
