package com.liuxinchi.hpe.controller;

import com.liuxinchi.hpe.common.ApiRestResponse;
import com.liuxinchi.hpe.common.Constant;
import com.liuxinchi.hpe.exception.HpeException;
import com.liuxinchi.hpe.exception.HpeExceptionEnum;
import com.liuxinchi.hpe.model.pojo.User;
import com.liuxinchi.hpe.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @author 拾荒老冰棍
 */
@Controller
public class UserController {

    @Autowired
    UserService userService;

    @ResponseBody
    @PostMapping("/register")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "用户名", required = false),
            @ApiImplicitParam(name = "password", value = "密码", required = false)
    }
    )
    public ApiRestResponse register(@RequestParam(value = "userName",required = false) String userName, @RequestParam(value = "password",required = false) String password) throws HpeException {
        if(StringUtils.isEmpty(userName)){
            return ApiRestResponse.error(HpeExceptionEnum.NEED_USER_NAME);
        }else if(StringUtils.isEmpty(password)){
            return ApiRestResponse.error(HpeExceptionEnum.NEED_PASSWORD);
        }else if(password.length()<8){
            return ApiRestResponse.error(HpeExceptionEnum.PASSWORD_TOO_SHORT);
        }else{
            userService.register(userName, password);
            return ApiRestResponse.success();
        }
    }

    @ResponseBody
    @PostMapping("/login")
    public ApiRestResponse login(@RequestParam(value = "userName",required = false) String userName, @RequestParam(value = "password",required = false) String password, HttpSession httpSession) throws HpeException{
        if(StringUtils.isEmpty(userName)){
            return ApiRestResponse.error(HpeExceptionEnum.NEED_USER_NAME);
        }else if(StringUtils.isEmpty(password)){
            return ApiRestResponse.error(HpeExceptionEnum.NEED_PASSWORD);
        }else{
            User user = userService.login(userName, password);
            user.setPassword(null);
            httpSession.setAttribute(Constant.HPE_USER,user);
            return ApiRestResponse.success(user);
        }
    }

    @ResponseBody
    @PostMapping("/user/update")
    public ApiRestResponse updatePassword(HttpSession httpSession, @RequestParam("password") String password) throws HpeException {
        User currentUser = (User) httpSession.getAttribute(Constant.HPE_USER);
        if(currentUser==null){
            return ApiRestResponse.error(HpeExceptionEnum.NEED_LOGIN);
        }
        Integer userId = currentUser.getId();
        userService.updateUser(password,userId);
        return ApiRestResponse.success();
    }

    @ResponseBody
    @PostMapping("/user/logout")
    public ApiRestResponse logout(HttpSession httpSession) throws HpeException {
        httpSession.removeAttribute(Constant.HPE_USER);
        return ApiRestResponse.success();
    }

}
