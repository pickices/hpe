package com.liuxinchi.hpe.model.query;

public class ImageQuery {

    private String imageName;

    private String originImage;

    public ImageQuery(String imageName, String originImage) {
        this.imageName = imageName;
        this.originImage = originImage;
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
}
