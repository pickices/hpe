package com.liuxinchi.hpe.service;

import com.liuxinchi.hpe.exception.HpeException;
import com.liuxinchi.hpe.model.pojo.Image;
import com.liuxinchi.hpe.model.request.UpdateImageRequest;

public interface ImageService {

    Image selectImageByName(String imageName);

    boolean addImage(String imageName, String originImage) throws HpeException;

    boolean updateImageByName(UpdateImageRequest updateImageRequest) throws HpeException;
}
