package com.liuxinchi.hpe.model.pojo;

import java.util.Date;

public class Image {
    private Integer id;

    private String imageName;

    private String originImage;

    private String skeletonImage;

    private String json;

    private String meshImage;

    private String mesh;

    public String getMeshImage() {
        return meshImage;
    }

    public String getMesh() {
        return mesh;
    }

    private Date createTime;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getOriginImage() {
        return originImage;
    }

    public void setOriginImage(String originImage) {
        this.originImage = originImage;
    }

    public String getSkeletonImage() {
        return skeletonImage;
    }

    public void setSkeletonImage(String skeletonImage) {
        this.skeletonImage = skeletonImage;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public void setMeshImage(String meshImage) {
        this.meshImage = meshImage;
    }

    public void setMesh(String mesh) {
        this.mesh = mesh;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}