package com.javaclimb.cate.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 评论
 */
public class Comment implements Serializable {
    /*主键*/
    private Integer id;
    /*用户id*/
    private Integer userId;
    /*评论类型（0美食1美食合集2窗口）*/
    private Byte type;
    /*美食id*/
    private Integer foodId;
    /*美食合集id*/
    private Integer foodListId;
    //窗口id
    private Integer foodWindowId;
    /*评论内容*/
    private String content;
    /*评论时间*/
    private Date createTime;
    /*评论点赞数*/
    private Integer up;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Integer getFoodId() {
        return foodId;
    }

    public void setFoodId(Integer foodId) {
        this.foodId = foodId;
    }

    public Integer getFoodListId() {
        return foodListId;
    }

    public void setFoodListId(Integer foodListId) {
        this.foodListId = foodListId;
    }

    public Integer getFoodWindowId() {
        return foodWindowId;
    }

    public void setFoodWindowId(Integer foodWindowId) {
        this.foodWindowId = foodWindowId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getUp() {
        return up;
    }

    public void setUp(Integer up) {
        this.up = up;
    }
}
