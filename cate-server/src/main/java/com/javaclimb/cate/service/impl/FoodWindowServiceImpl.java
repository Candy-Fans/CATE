package com.javaclimb.cate.service.impl;

import com.javaclimb.cate.dao.FoodWindowMapper;
import com.javaclimb.cate.domain.FoodWindow;
import com.javaclimb.cate.service.FoodWindowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 窗口service实现类
 */
@Service
public class FoodWindowServiceImpl implements FoodWindowService {

    @Autowired
    private FoodWindowMapper foodWindowMapper;

    /**
     * 增加
     *
     * @param foodWindow
     */
    @Override
    public boolean insert(FoodWindow foodWindow) {
        return foodWindowMapper.insert(foodWindow)>0;
    }

    /**
     * 修改
     *
     * @param foodWindow
     */
    @Override
    public boolean update(FoodWindow foodWindow) {
        return foodWindowMapper.update(foodWindow)>0;
    }

    /**
     * 删除
     *
     * @param id
     */
    @Override
    public boolean delete(Integer id) {
        return foodWindowMapper.delete(id)>0;
    }

    /**
     * 根据主键查询整个对象
     *
     * @param id
     */
    @Override
    public FoodWindow selectByPrimaryKey(Integer id) {
        return foodWindowMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询所有窗口
     */
    @Override
    public List<FoodWindow> allFoodWindow() {
        return foodWindowMapper.allFoodWindow();
    }

    /**
     * 根据窗口名字模糊查询列表
     *
     * @param name
     */
    @Override
    public List<FoodWindow> foodWindowOfName(String name) {
        return foodWindowMapper.foodWindowOfName(name);
    }

    /**
     * 根据性别查询
     *
     * @param sex
     */
    @Override
    public List<FoodWindow> foodWindowOfSex(Integer sex) {
        return foodWindowMapper.foodWindowOfSex(sex);
    }
}
