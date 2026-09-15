package com.javaclimb.cate.domain;

public class Mark {
    /*主键*/
    private Integer id;
    /*用户id*/
    private Integer consumerId;
    /*评论类型（0美食1美食合集2窗口）*/
    private Byte typeS;
    /*美食id*/
    private Integer foodId;
    /*美食合集id*/
    private Integer foodListId;
    //窗口id
    private Integer foodWindowId;
    /*评分*/
    private Integer score;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(Integer consumerId) {
        this.consumerId = consumerId;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Byte getTypeS() {
        return typeS;
    }

    public void setTypeS(Byte typeS) {
        this.typeS = typeS;
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
}
