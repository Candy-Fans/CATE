package com.javaclimb.cate.service;

import com.javaclimb.cate.domain.FoodWindow;

import java.util.List;

/**
 * 窗口service接口
 */
public interface FoodWindowService {
    /**
     *增加
     */
    public boolean insert(FoodWindow foodWindow);

    /**
     *修改
     */
    public boolean update(FoodWindow foodWindow);

    /**
     * 删除
     */
    public boolean delete(Integer id);

    /**
     * 根据主键查询整个对象
     */
    public FoodWindow selectByPrimaryKey(Integer id);

    /**
     * 查询所有窗口
     */
    public List<FoodWindow> allFoodWindow();

    /**
     * 根据窗口名字模糊查询列表
     */
    public List<FoodWindow> foodWindowOfName(String name);

    /**
     * 根据性别查询
     */
    public List<FoodWindow> foodWindowOfSex(Integer sex);
}
