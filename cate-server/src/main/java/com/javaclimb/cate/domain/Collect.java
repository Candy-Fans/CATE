package com.javaclimb.cate.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 收藏
 */
public class Collect implements Serializable {
    private Integer id;     //主键
    private Integer userId; //用户id
    private Byte type;      //收藏类型（0美食1美食合集）
    private Integer foodId; //美食id
    private Integer foodListId; //美食合集id
    private Date createTime;    //收藏时间

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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
