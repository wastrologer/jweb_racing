package com.pojo;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.sql.Timestamp;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "userId")
public class User {
    private Integer userId;//不填，自动生成
    private String authId;//不填，自动生成，权限ID
    //private String authName;//不填，自动生成，权限名称
    private  Integer regionId;//必填，地区ID，数字对应区级，限制数字范围
    //private String regionName;//不填，自动生成，地区名称
    private String userName;//可不填，用户名，默认手机号，填的会校验唯一性
    private String password;//必填，校验合法性
    private String phoneNumber;//必填，校验合法性
    private String userPic;//头像，可不填，填上传图片的src
    private String backdrop;//背景，可不填，填上传图片的src
    private  Integer userGold;//不填，默认0
    private  Integer userCoin;//不填，默认0
    private  Integer inviteNum;//不填，默认0
    private  Integer concern;//不填，默认0
    private  Integer fans;//不填，默认0
    private String inviteCode;//不填，自动生成
    private String signature;//可不填，填的有长度限制
    private String nickname;//可不填，默认手机号，填的有长度限制
    private Boolean concerned;//返回值，是否关注该用户
   // private  Integer indentified;//不填，默认0
   private Integer inviteUserId;//不填，自动生成
    private Boolean mutualConcerned;//不填，自动生成,互相关注
    private Integer userType;
    private Timestamp createTime;
    private Timestamp lastUpdateTime;
    private Timestamp loginTime;
    private String authenticationTime;
    public Boolean getMutualConcerned() {
        return mutualConcerned;
    }
    private Integer isDisable;//不填，1不可用，2可用（是否拉黑）
    private Integer nicknameChanged;//Nickname 是否改动过


    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", authId='" + authId + '\'' +
                ", regionId=" + regionId +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", userPic='" + userPic + '\'' +
                ", backdrop='" + backdrop + '\'' +
                ", userGold=" + userGold +
                ", userCoin=" + userCoin +
                ", inviteNum=" + inviteNum +
                ", concern=" + concern +
                ", fans=" + fans +
                ", inviteCode='" + inviteCode + '\'' +
                ", signature='" + signature + '\'' +
                ", nickname='" + nickname + '\'' +
                ", concerned=" + concerned +
                ", inviteUserId=" + inviteUserId +
                ", mutualConcerned=" + mutualConcerned +
                ", userType=" + userType +
                ", createTime=" + createTime +
                ", lastUpdateTime=" + lastUpdateTime +
                ", loginTime=" + loginTime +
                ", isDisable=" + isDisable +
                '}';
    }

    public Integer getNicknameChanged() {
        return nicknameChanged;
    }

    public void setNicknameChanged(Integer nicknameChanged) {
        this.nicknameChanged = nicknameChanged;
    }

    public Integer getIsDisable() {
        return isDisable;
    }

    public void setIsDisable(Integer isDisable) {
        this.isDisable = isDisable;
    }

    public Timestamp getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Timestamp loginTime) {
        this.loginTime = loginTime;
    }

    public void setMutualConcerned(Boolean mutualConcerned) {
        this.mutualConcerned = mutualConcerned;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Integer getInviteUserId() {
        return inviteUserId;
    }

    public void setInviteUserId(Integer inviteUserId) {
        this.inviteUserId = inviteUserId;
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



    public Boolean getConcerned() {
        return concerned;
    }

    public void setConcerned(Boolean concerned) {
        this.concerned = concerned;
    }

    public String getBackdrop() {
        return backdrop;
    }

    public void setBackdrop(String backdrop) {
        this.backdrop = backdrop;
    }

    public Integer getConcern() {
        return concern;
    }

    public void setConcern(Integer concern) {
        this.concern = concern;
    }

    public Integer getFans() {
        return fans;
    }

    public void setFans(Integer fans) {
        this.fans = fans;
    }

    public  Integer getInviteNum() {
        return inviteNum;
    }

    public void setInviteNum( Integer inviteNum) {
        this.inviteNum = inviteNum;
    }

    public User getInstance(){
        return this;
    }

    public String getAuthId() {
        return authId;
    }

    public void setAuthId(String authId) {
        this.authId = authId;
    }

    public  Integer getRegionId() {
        return regionId;
    }

    public void setRegionId( Integer regionId) {
        this.regionId = regionId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public  Integer getUserId() {
        return userId;
    }

    public void setUserId( Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUserPic() {
        return userPic;
    }

    public void setUserPic(String userPic) {
        this.userPic = userPic;
    }

    public  Integer getUserGold() {
        return userGold;
    }

    public void setUserGold( Integer userGold) {
        this.userGold = userGold;
    }

    public  Integer getUserCoin() {
        return userCoin;
    }

    public void setUserCoin( Integer userCoin) {
        this.userCoin = userCoin;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

	public String getAuthenticationTime() {
		return authenticationTime;
	}

	public void setAuthenticationTime(String authenticationTime) {
		this.authenticationTime = authenticationTime;
	}
}
