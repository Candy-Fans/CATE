package com.javaclimb.cate.service;

import com.javaclimb.cate.domain.Food;

import java.util.List;

/**
 * 美食service接口
 */
public interface FoodService {
    /**
     *增加
     */
    public boolean insert(Food food);

    /**
     *修改
     */
    public boolean update(Food food);

    /**
     * 删除
     */
    public boolean delete(Integer id);

    /**
     * 根据主键查询整个对象
     */
    public Food selectByPrimaryKey(Integer id);
    
    /**
     *增加播放次数
     */
    public boolean addNums(Integer id);

    /**
     * 查询所有美食
     */
    public List<Food> allFood();

    /**
     * 根据美食名精确查询列表
     */
    public List<Food> foodOfName(String name);

    /**
     * 根据美食名模糊查询列表
     */
    public List<Food> likeFoodOfName(String name);

    /**
     * 根据窗口id查询
     */
    public List<Food> foodOfFoodWindowId(Integer foodWindowId);

    /**
     * 查询播放次数排前列的美食
     */
	public List<Food> topFood();
    /**
     * 随机id展示
     */
    public Food randomFood();

}
