package com.javaclimb.cate.dao;

import com.javaclimb.cate.domain.FoodList;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 美食合集Dao
 */
@Repository
public interface FoodListMapper {
    /**
     *增加
     */
    public int insert(FoodList foodList);

    /**
     *修改
     */
    public int update(FoodList foodList);

    /**
     * 删除
     */
    public int delete(Integer id);

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
















