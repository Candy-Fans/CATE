package com.javaclimb.cate.dao;

import com.javaclimb.cate.domain.FoodWindow;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 窗口Dao
 */
@Repository
public interface FoodWindowMapper {
    /**
     *增加
     */
    public int insert(FoodWindow foodWindow);

    /**
     *修改
     */
    public int update(FoodWindow foodWindow);

    /**
     * 删除
     */
    public int delete(Integer id);

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
















