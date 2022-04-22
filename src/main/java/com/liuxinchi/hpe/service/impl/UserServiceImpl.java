package com.liuxinchi.hpe.service.impl;

import com.liuxinchi.hpe.exception.HpeException;
import com.liuxinchi.hpe.exception.HpeExceptionEnum;
import com.liuxinchi.hpe.model.dao.UserMapper;
import com.liuxinchi.hpe.service.UserService;
import com.liuxinchi.hpe.model.pojo.User;
import com.liuxinchi.hpe.util.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public void register(String userName, String password) throws HpeException {
        if(userMapper.selectByName(userName)!=null){
            throw new HpeException(HpeExceptionEnum.NAME_EXISTED);
        }

        User user = new User();
        user.setUsername(userName);
        try {
            user.setPassword(Md5Util.getMd5Str(password));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        if(userMapper.insertSelective(user)==0){
            throw new HpeException(HpeExceptionEnum.INSERT_FAILED);
        }

    }

    @Override
    public User login(String userName, String password) throws HpeException {
        String md5Password = null;
        try {
            md5Password = Md5Util.getMd5Str(password);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        User user = userMapper.selectByNameAndPwd(userName, md5Password);

        if(user==null){
            throw new HpeException(HpeExceptionEnum.WRONG_PASSWORD);
        }

        return user;

    }

    @Override
    public void updateUser(String password, Integer userId) throws HpeException {
        User user = new User();
        try {
            user.setPassword(Md5Util.getMd5Str(password));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        user.setId(userId);
        if(userMapper.updateByPrimaryKeySelective(user)!=1){
            throw new HpeException(HpeExceptionEnum.UPDATE_FAILED);
        }
    }

}
