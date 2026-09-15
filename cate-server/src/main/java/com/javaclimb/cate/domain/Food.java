package com.javaclimb.cate.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 美食
 */
@Data
public class Food implements Serializable {
    /*主键*/
    private Integer id;
    //窗口id
    private Integer foodWindowId;
    /*美食名*/
    private String name;
    /*简介*/
    private String introduction;
    /*创建时间*/
    private Date createTime;
    /*更新时间*/
    private Date updateTime;
    /*美食图片*/
    private String pic;

    /*美食价格*/
    private Integer url;
    private Integer price;

    /*美食播放次数*/
    private Integer nums;



}
