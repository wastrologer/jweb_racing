package com.pojo;

import java.util.Date;

public class PlatformContact {
    private Long id;

    private String contactName;

    private Integer type;

    private String image;

    private String text;

    private Integer isDel;

    private Date createTime;

    private Date lastupdatetime;

    @Override
    public String toString() {
        return "PlatformContact{" +
                "id=" + id +
                ", contactName='" + contactName + '\'' +
                ", type=" + type +
                ", image='" + image + '\'' +
                ", text='" + text + '\'' +
                ", isDel=" + isDel +
                ", createTime=" + createTime +
                ", lastupdatetime=" + lastupdatetime +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName == null ? null : contactName.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image == null ? null : image.trim();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text == null ? null : text.trim();
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastupdatetime() {
        return lastupdatetime;
    }

    public void setLastupdatetime(Date lastupdatetime) {
        this.lastupdatetime = lastupdatetime;
    }
}