package com.pojo;

import java.sql.Timestamp;

public class Concern {
    private Integer concernId;
    private Integer userFromId;
    private Integer userToId;
    private Timestamp createTime;
    private Timestamp lastUpdateTime;

    @Override
    public String toString() {
        return "Concern{" +
                "concernId=" + concernId +
                ", userFromId=" + userFromId +
                ", userToId=" + userToId +
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

    public Integer getConcernId() {
        return concernId;
    }

    public void setConcernId(Integer concernId) {
        this.concernId = concernId;
    }

    public Integer getUserFromId() {
        return userFromId;
    }

    public void setUserFromId(Integer userFromId) {
        this.userFromId = userFromId;
    }

    public Integer getUserToId() {
        return userToId;
    }

    public void setUserToId(Integer userToId) {
        this.userToId = userToId;
    }
}
