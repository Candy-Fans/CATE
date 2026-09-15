package com.javaclimb.cate.service.impl;

import com.javaclimb.cate.dao.FoodMapper;
import com.javaclimb.cate.domain.Food;
import com.javaclimb.cate.service.FoodService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 美食service实现类
 */
@Service
@Slf4j
public class FoodServiceImpl implements FoodService {
    @Autowired
    private FoodMapper foodMapper;

    /**
     * 增加
     *
     * @param food
     */
    @Override
    public boolean insert(Food food) {
        return foodMapper.insert(food) > 0;
    }

    /**
     * 修改
     *
     * @param food
     */
    @Override
    public boolean update(Food food) {
        return foodMapper.update(food) > 0;
    }

    /**
     * 删除
     *
     * @param id
     */
    @Override
    public boolean delete(Integer id) {
        return foodMapper.delete(id) > 0;
    }

    /**
     * 根据主键查询整个对象
     *
     * @param id
     */
    @Override
    public Food selectByPrimaryKey(Integer id) {
        return foodMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询所有美食
     */
    @Override
    public List<Food> allFood() {
        return foodMapper.allFood();
    }

    /**
     * 根据美食名精确查询列表
     *
     * @param name
     */
    @Override
    public List<Food> foodOfName(String name) {
        return foodMapper.foodOfName(name);
    }

    /**
     * 根据美食名模糊查询列表
     *
     * @param name
     */
    @Override
    public List<Food> likeFoodOfName(String name) {
        //log.error(">>>>>>>>>>>>>搜索的名字{}", name);

        List<Food> foods = foodMapper.likeFoodOfName("%" + name + "%");
        //foods.forEach(food -> log.warn(">>>>>>>>>>>>>>{}", food.getIsVip()));
        return foods;
    }


    /**
     * 根据窗口id查询
     *
     * @param foodWindowId
     */
    @Override
    public List<Food> foodOfFoodWindowId(Integer foodWindowId) {
        return foodMapper.foodOfFoodWindowId(foodWindowId);
    }

    @Override
    public boolean addNums(Integer id) {
        return foodMapper.addNums(id);
    }

    @Override
    public List<Food> topFood() {
        return foodMapper.topFood();
    }

    @Override
    public Food randomFood() {
        // 返回随机食物
        return foodMapper.randomFood();
    }

}
