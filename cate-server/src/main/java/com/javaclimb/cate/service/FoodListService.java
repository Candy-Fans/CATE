package com.javaclimb.cate.service;

import com.javaclimb.cate.domain.FoodList;

import java.util.List;

/**
 * 美食合集service接口
 */
public interface FoodListService {
    /**
     *增加
     */
    public boolean insert(FoodList foodList);

    /**
     *修改
     */
    public boolean update(FoodList foodList);

    /**
     * 删除
     */
    public boolean delete(Integer id);

    /**
     * 根据主键查询整个对象
     */
    public FoodList selectByPrimaryKey(Integer id);

    /**
     * 查询所有美食合集
     */
    public List<FoodList> allFoodList();

    /**
     * 根据标题精确查询美食合集列表
     */
    public List<FoodList> foodListOfTitle(String title);

    /**
     * 根据标题模糊查询美食合集列表
     */
    public List<FoodList> likeTitle(String title);

    /**
     * 根据风格模糊查询美食合集列表
     */
    public List<FoodList> likeStyle(String style);

}
