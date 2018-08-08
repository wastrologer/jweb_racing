package com.pojo;

import java.sql.Timestamp;

public class Identification {
    private int pic;
    private int nickname;
    private int essays;
    private int signature;
    private int fans;
    private int isIdentified;
    private Timestamp createTime;
    private Timestamp lastUpdateTime;

    @Override
    public String toString() {
        return "Identification{" +
                "pic=" + pic +
                ", nickname=" + nickname +
                ", essays=" + essays +
                ", signature=" + signature +
                ", fans=" + fans +
                ", isIdentified=" + isIdentified +
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

    public int getIsIdentified() {
        return isIdentified;
    }

    public void setIsIdentified(int isIdentified) {
        this.isIdentified = isIdentified;
    }

    public int getPic() {
        return pic;
    }

    public void setPic(int pic) {
        this.pic = pic;
    }

    public int getNickname() {
        return nickname;
    }

    public void setNickname(int nickname) {
        this.nickname = nickname;
    }

    public int getEssays() {
        return essays;
    }

    public void setEssays(int essays) {
        this.essays = essays;
    }

    public int getSignature() {
        return signature;
    }

    public void setSignature(int signature) {
        this.signature = signature;
    }

    public int getFans() {
        return fans;
    }

    public void setFans(int fans) {
        this.fans = fans;
    }

    public Identification(int pic, int nickname, int essays, int signature, int fans,int isIdentified) {
        this.pic = pic;
        this.nickname = nickname;
        this.essays = essays;
        this.signature = signature;
        this.fans = fans;
        this.isIdentified = isIdentified;
    }

    public Identification() {
    }
}
