package com.pojo;

import java.io.Serializable;
import java.sql.Timestamp;

public class Message implements Serializable{

    private static final long serialVersionUID = -3883060416348020376L;
    private Integer messageId;
    private Integer messageUserId;
    private String messageContent;
    private Integer isRead;
    private Integer messageEssayId;
    private Integer messageType;//0系统，1文章，2新鲜事，3评论
    private Integer senderUserId;
    private Timestamp createTime;
    private Timestamp lastUpdateTime;

    @Override
    public String toString() {
        return "Message{" +
                "messageId=" + messageId +
                ", messageUserId=" + messageUserId +
                ", messageContent='" + messageContent + '\'' +
                ", isRead=" + isRead +
                ", messageEssayId=" + messageEssayId +
                ", messageType=" + messageType +
                ", senderUserId=" + senderUserId +
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

    public Integer getIsRead() {
        return isRead;
    }

    public void setIsRead(Integer isRead) {
        this.isRead = isRead;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getSenderUserId() {
        return senderUserId;
    }

    public void setSenderUserId(Integer senderUserId) {
        this.senderUserId = senderUserId;
    }

    public Integer getMessageEssayId() {
        return messageEssayId;
    }

    public void setMessageEssayId(Integer messageEssayId) {
        this.messageEssayId = messageEssayId;
    }

    public Integer getMessageType() {
        return messageType;
    }

    public void setMessageType(Integer messageType) {
        this.messageType = messageType;
    }

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public Integer getMessageUserId() {
        return messageUserId;
    }

    public void setMessageUserId(Integer messageUserId) {
        this.messageUserId = messageUserId;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

}
