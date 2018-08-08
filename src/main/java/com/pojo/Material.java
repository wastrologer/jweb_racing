package com.pojo;


import com.common.entity.BaseDo;

import java.sql.Timestamp;

public class Material extends BaseDo {
	
	private static final long serialVersionUID = 5161658878045450705L;

	private String materialId;

    private Byte materialFormate;

    private Timestamp gatherTime;

    private String gatherDay;

    private String materialName;

    private Byte isdel;

    private Integer height;

    private Integer width;

    private Timestamp createTime;

    private Timestamp lastUpdateTime;

    @Override
    public String toString() {
        return "Material{" +
                "materialId='" + materialId + '\'' +
                ", materialFormate=" + materialFormate +
                ", gatherTime=" + gatherTime +
                ", gatherDay='" + gatherDay + '\'' +
                ", materialName='" + materialName + '\'' +
                ", isdel=" + isdel +
                ", height=" + height +
                ", width=" + width +
                ", createTime=" + createTime +
                ", lastUpdateTime=" + lastUpdateTime +
                '}';
    }

    public String getMaterialId() {
        return materialId;
    }

    public void setMaterialId(String materialId) {
        this.materialId = materialId == null ? null : materialId.trim();
    }

    public Byte getMaterialFormate() {
        return materialFormate;
    }

    public void setMaterialFormate(Byte materialFormate) {
        this.materialFormate = materialFormate;
    }

    public String getGatherDay() {
        return gatherDay;
    }

    public void setGatherDay(String gatherDay) {
        this.gatherDay = gatherDay == null ? null : gatherDay.trim();
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName == null ? null : materialName.trim();
    }

    public Byte getIsdel() {
        return isdel;
    }

    public void setIsdel(Byte isdel) {
        this.isdel = isdel;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Timestamp getGatherTime() {
        return gatherTime;
    }

    public void setGatherTime(Timestamp gatherTime) {
        this.gatherTime = gatherTime;
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
}