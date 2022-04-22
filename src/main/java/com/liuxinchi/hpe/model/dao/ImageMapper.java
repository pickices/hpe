package com.liuxinchi.hpe.model.dao;

import com.liuxinchi.hpe.model.pojo.Image;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface ImageMapper {

    Image selectImageByName(String imageName);

    int insert(Image image);

    int insertSelective(Image image);

    int updateByNameSelective(Image image);
}
