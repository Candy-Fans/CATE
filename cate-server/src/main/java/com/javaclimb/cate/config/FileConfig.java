package com.javaclimb.cate.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 定位各种文件或头像地址
 */
@Configuration
public class FileConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        //窗口头像地址
        registry.addResourceHandler("/img/foodWindowPic/**").addResourceLocations(
                "file:"+System.getProperty("user.dir")+System.getProperty("file.separator")+"img"
                +System.getProperty("file.separator")+"foodWindowPic"+System.getProperty("file.separator")
        );
        //美食合集图片地址
        registry.addResourceHandler("/img/foodListPic/**").addResourceLocations(
                "file:"+System.getProperty("user.dir")+System.getProperty("file.separator")+"img"
                        +System.getProperty("file.separator")+"foodListPic"+System.getProperty("file.separator")
        );
        //美食图片地址
        registry.addResourceHandler("/img/foodPic/**").addResourceLocations(
                "file:"+System.getProperty("user.dir")+System.getProperty("file.separator")+"img"
                        +System.getProperty("file.separator")+"foodPic"+System.getProperty("file.separator")
        );
        //美食地址
        registry.addResourceHandler("/food/**").addResourceLocations(
                "file:"+System.getProperty("user.dir")+System.getProperty("file.separator")+"food"+System.getProperty("file.separator")
        );
        //美食MV地址
        registry.addResourceHandler("/mv/**").addResourceLocations(
                "file:"+System.getProperty("user.dir")+System.getProperty("file.separator")+"mv"+System.getProperty("file.separator")
        );
        //前端用户头像地址
        registry.addResourceHandler("/avatorImages/**").addResourceLocations(
                "file:"+System.getProperty("user.dir")+System.getProperty("file.separator")+"avatorImages"+System.getProperty("file.separator")
        );
        //用户头像默认地址
        registry.addResourceHandler("/img/**").addResourceLocations(
                "file:"+System.getProperty("user.dir")+System.getProperty("file.separator")+"img"+System.getProperty("file.separator")
        );
    }
}

















