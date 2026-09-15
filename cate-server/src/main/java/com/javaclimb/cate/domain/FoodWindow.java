package com.javaclimb.cate.domain;

import java.io.Serializable;

/**
 * 窗口
 */
public class FoodWindow implements Serializable {
    /*主键*/
    private Integer id;
    /*名字*/
    private String name;
    /*性别*/
    private Byte sex;
    /*头像*/
    private String pic;
    /*简介*/
    private String introduction;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Byte getSex() {
        return sex;
    }

    public void setSex(Byte sex) {
        this.sex = sex;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }
}
