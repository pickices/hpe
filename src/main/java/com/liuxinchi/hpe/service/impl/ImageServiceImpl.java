package com.liuxinchi.hpe.service.impl;

import com.liuxinchi.hpe.exception.HpeException;
import com.liuxinchi.hpe.exception.HpeExceptionEnum;
import com.liuxinchi.hpe.model.dao.ImageMapper;
import com.liuxinchi.hpe.model.pojo.Image;
import com.liuxinchi.hpe.model.request.UpdateImageRequest;
import com.liuxinchi.hpe.service.ImageService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    ImageMapper imageMapper;

    @Override
    public Image selectImageByName(String imageName) {
        return imageMapper.selectImageByName(imageName);

    }

    @Override
    public boolean addImage(String imageName, String originImage) throws HpeException {
        Image image = new Image();
        image.setImageName(imageName);
        image.setOriginImage(originImage);
        if(imageMapper.insertSelective(image)==0){
            throw new HpeException(HpeExceptionEnum.INSERT_FAILED);
        }
        return true;
    }

    @Override
    public boolean updateImageByName(UpdateImageRequest updateImageRequest) throws HpeException {
        Image image = new Image();
        BeanUtils.copyProperties(updateImageRequest,image);
        if(imageMapper.updateByNameSelective(image)==0){
            throw new HpeException(HpeExceptionEnum.UPLOAD_FAILED);
        }
        return true;
    }
}
