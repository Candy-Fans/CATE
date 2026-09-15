package com.javaclimb.cate.domain;

import java.io.Serializable;

/**
 * 美食合集里面的美食
 */
public class ListFood implements Serializable {

    private Integer id;     //主键

    private Integer foodId; //美食id

    private Integer foodListId; //美食合集id

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
}
