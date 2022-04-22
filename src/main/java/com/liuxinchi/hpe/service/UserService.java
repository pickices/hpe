package com.liuxinchi.hpe.service;

import com.liuxinchi.hpe.exception.HpeException;
import com.liuxinchi.hpe.model.pojo.User;

/**
 * @author 拾荒老冰棍
 */
public interface UserService {

    void register(String userName, String password) throws HpeException;

    User login(String userName, String password) throws HpeException;

    void updateUser(String password, Integer userId) throws HpeException;
    
}
