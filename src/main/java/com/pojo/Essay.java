package com.pojo;

import java.sql.Timestamp;
import java.util.List;

public class Essay {
    private  boolean isHot;//返回字段，自动填入，是否热门，true热门，false不热
    private  String timeBefore;//返回字段，自动填入，发布多久？？？
    private Integer isPublished;//可不填，默认0，限制数字范围，是否发布，0草稿，1发布
    private Integer essayId;//不填，自动生成，文章ID
    private Integer userId;//不填，自动填入，发布用户ID
    private String userNickname;//不填，自动填入，发布用户昵称
    private String essayTitle;//必填，限制长度，标题
    private String essayContent;//必填，限制长度，内容
    private String essayPic;//可不填，缩略图src
    private Integer essayType;//必填，限制数字范围，0系统，1日报，2会所，3新人学堂，4新鲜事，
    //private String essayTypeName;//不填，自动生成，
    private Integer recommendNum;//不填，默认0，推荐数量
    private Integer commentNum;//不填，默认0，评论数量
    private Integer clickNum;//不填，默认0，点击数量
    private Integer regionId;//可不填，自动填入，默认用户所在地，限制数字范围
    //private String regionName;//不填，自动生成，用户所在地名称
    private Timestamp publishTime;//不填，自动生成，发布时间
    private Timestamp createTime;//不填，自动生成，创建时间
    private Integer topicId;//可不填，主题ID，限制数字范围
    //private String topicName;//不填，自动生成，主题名称
    private Boolean collected;//返回值，是否收藏了文章
    private Boolean recommended;//返回值，是否推荐了文章
    private Boolean concerned;//返回值，是否关注了作者
    private Integer goldNum;//不填,奖励金币
    private Integer isAward;//不填,是否奖励
    private Integer isHiden=1;//不填，自动生成，1不隐藏，2隐藏
    private Integer collectNum;//返回值，是否收藏了文章
    private Integer cityId;//1深圳，2广州

    private String authId;//作者auth
    private String userPic;//
    private Timestamp lastUpdateTime;

    @Override
    public String toString() {
        return "Essay{" +
                "isHot=" + isHot +
                ", timeBefore='" + timeBefore + '\'' +
                ", isPublished=" + isPublished +
                ", essayId=" + essayId +
                ", userId=" + userId +
                ", userNickname='" + userNickname + '\'' +
                ", essayTitle='" + essayTitle + '\'' +
                ", essayContent='" + essayContent + '\'' +
                ", essayPic='" + essayPic + '\'' +
                ", essayType=" + essayType +
                ", recommendNum=" + recommendNum +
                ", commentNum=" + commentNum +
                ", clickNum=" + clickNum +
                ", regionId=" + regionId +
                ", publishTime=" + publishTime +
                ", createTime=" + createTime +
                ", topicId=" + topicId +
                ", collected=" + collected +
                ", recommended=" + recommended +
                ", concerned=" + concerned +
                ", goldNum=" + goldNum +
                ", isAward=" + isAward +
                ", isHiden=" + isHiden +
                ", collectNum=" + collectNum +
                ", userPic='" + userPic + '\'' +
                ", lastUpdateTime=" + lastUpdateTime +
                ", comments=" + comments +
                '}';
    }

    public String getAuthId() {
        return authId;
    }

    public void setAuthId(String authId) {
        this.authId = authId;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getCollectNum() {
        return collectNum;
    }

    public void setCollectNum(Integer collectNum) {
        this.collectNum = collectNum;
    }

    public Integer getIsAward() {
        return isAward;
    }

    public void setIsAward(Integer isAward) {
        this.isAward = isAward;
    }

    public Integer getIsHiden() {
        return isHiden;
    }

    public void setIsHiden(Integer isHiden) {
        this.isHiden = isHiden;
    }

    public Integer getGoldNum() {
        return goldNum;
    }

    public void setGoldNum(Integer goldNum) {
        this.goldNum = goldNum;
    }

    public Boolean getConcerned() {
        return concerned;
    }

    public void setConcerned(Boolean concerned) {
        this.concerned = concerned;
    }

    public Timestamp getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Timestamp lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    private List<Comment> comments;

    public String getUserPic() {
        return userPic;
    }

    public void setUserPic(String userPic) {
        this.userPic = userPic;
    }

    public Boolean getCollected() {
        return collected;
    }

    public void setCollected(Boolean collected) {
        this.collected = collected;
    }

    public Boolean getRecommended() {
        return recommended;
    }

    public void setRecommended(Boolean recommended) {
        this.recommended = recommended;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public boolean isHot() {
        return isHot;
    }


    public void setHot(boolean hot) {
        isHot = hot;
    }

    public String getTimeBefore() {
        return timeBefore;
    }

    public void setTimeBefore(String timeBefore) {
        this.timeBefore = timeBefore;
    }

    public Essay(Integer isPublished) {
        this.isPublished = isPublished;
    }

    public Essay() {
    }

    public Integer getIsPublished() {
        return isPublished;
    }

    public void setIsPublished(Integer isPublished) {
        this.isPublished = isPublished;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Integer getRegionId() {
        return regionId;
    }

    public void setRegionId(Integer regionId) {
        this.regionId = regionId;
    }

    public Integer getRecommendNum() {
        return recommendNum;
    }

    public void setRecommendNum(Integer recommendNum) {
        this.recommendNum = recommendNum;
    }

    public Integer getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(Integer commentNum) {
        this.commentNum = commentNum;
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

    public String getEssayPic() {
        return essayPic;
    }

    public void setEssayPic(String essayPic) {
        this.essayPic = essayPic;
    }

    public Integer getEssayType() {
        return essayType;
    }

    public void setEssayType(Integer essayType) {
        this.essayType = essayType;
    }

    public Integer getClickNum() {
        return clickNum;
    }

    public void setClickNum(Integer clickNum) {
        this.clickNum = clickNum;
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

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }


}
