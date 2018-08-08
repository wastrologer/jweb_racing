package com.pojo;

import java.sql.Timestamp;

public class Accusation {
    private Integer accusationId;
    private String accusationContent;
    private Integer essayId;
    private Integer userId;
    private Timestamp createTime;
    private Timestamp lastUpdateTime;
    private Integer auditState;

    @Override
    public String toString() {
        return "Accusation{" +
                "accusationId=" + accusationId +
                ", accusationContent='" + accusationContent + '\'' +
                ", essayId=" + essayId +
                ", userId=" + userId +
                ", createTime=" + createTime +
                ", lastUpdateTime=" + lastUpdateTime +
                ", auditState=" + auditState +
                '}';
    }

    public Integer getAuditState() {
        return auditState;
    }

    public void setAuditState(Integer auditState) {
        this.auditState = auditState;
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

    public Integer getAccusationId() {
        return accusationId;
    }

    public void setAccusationId(Integer accusationId) {
        this.accusationId = accusationId;
    }

    public String getAccusationContent() {
        return accusationContent;
    }

    public void setAccusationContent(String accusationContent) {
        this.accusationContent = accusationContent;
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

}
