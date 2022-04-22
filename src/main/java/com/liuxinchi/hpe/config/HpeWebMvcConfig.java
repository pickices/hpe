package com.liuxinchi.hpe.config;

import com.liuxinchi.hpe.common.Constant;
import com.liuxinchi.hpe.interceptor.UserInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 拾荒老冰棍
 */
@Configuration
public class HpeWebMvcConfig implements WebMvcConfigurer {

    @Autowired
    UserInterceptor userInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry){
//        registry.addInterceptor(userInterceptor).addPathPatterns("/image/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler("/upload/**").addResourceLocations("file:"+ Constant.FILE_UPLOAD_DIR);
        registry.addResourceHandler("/output/**").addResourceLocations("file:/home/liuxinchi/hpe/output/");
    }
}
