package com.javaclimb.cate.dao;

import com.javaclimb.cate.domain.Food;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 窗口Dao
 */
@Repository
public interface FoodMapper {
    /**
     *增加
     */
    public int insert(Food food);

    /**
     *修改
     */
    public int update(Food food);

    /**
     * 删除
     */
    public int delete(Integer id);

    /**
     * 根据主键查询整个对象
     */
    public Food selectByPrimaryKey(Integer id);

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
     * 增加美食播放次数
     */
	public boolean addNums(Integer id);

	/**
     * 查询播放次数排前列的美食
     */
	public List<Food> topFood();

	public Food randomFood();
}
















