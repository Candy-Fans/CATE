package com.javaclimb.cate.service.impl;

import com.javaclimb.cate.dao.FoodListMapper;
import com.javaclimb.cate.domain.FoodList;
import com.javaclimb.cate.service.FoodListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 美食合集service实现类
 */
@Service
public class FoodListServiceImpl implements FoodListService {

    @Autowired
    private FoodListMapper foodListMapper;


    /**
     * 增加
     *
     * @param foodList
     */
    @Override
    public boolean insert(FoodList foodList) {
        return foodListMapper.insert(foodList)>0;
    }

    /**
     * 修改
     *
     * @param foodList
     */
    @Override
    public boolean update(FoodList foodList) {
        return foodListMapper.update(foodList)>0;
    }

    /**
     * 删除
     *
     * @param id
     */
    @Override
    public boolean delete(Integer id) {
        return foodListMapper.delete(id)>0;
    }

    /**
     * 根据主键查询整个对象
     *
     * @param id
     */
    @Override
    public FoodList selectByPrimaryKey(Integer id) {
        return foodListMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询所有美食合集
     */
    @Override
    public List<FoodList> allFoodList() {
        return foodListMapper.allFoodList();
    }

    /**
     * 根据标题精确查询美食合集列表
     *
     * @param title
     */
    @Override
    public List<FoodList> foodListOfTitle(String title) {
        return foodListMapper.foodListOfTitle(title);
    }

    /**
     * 根据标题模糊查询美食合集列表
     *
     * @param title
     */
    @Override
    public List<FoodList> likeTitle(String title) {
        return foodListMapper.likeTitle(title);
    }

    /**
     * 根据风格模糊查询美食合集列表
     *
     * @param style
     */
    @Override
    public List<FoodList> likeStyle(String style) {
        return foodListMapper.likeStyle(style);
    }
}
