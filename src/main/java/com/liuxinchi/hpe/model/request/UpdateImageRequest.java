package com.liuxinchi.hpe.model.request;

public class UpdateImageRequest {
    private String imageName;

    private String originImage;

    private String skeletonImage;

    private String json;

    private String meshImage;

    private String mesh;

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

    public String getMeshImage() {
        return meshImage;
    }

    public void setMeshImage(String meshImage) {
        this.meshImage = meshImage;
    }

    public String getMesh() {
        return mesh;
    }

    public void setMesh(String mesh) {
        this.mesh = mesh;
    }
}
