package com.javaclimb.cate.service.impl;

import com.javaclimb.cate.dao.ListFoodMapper;
import com.javaclimb.cate.domain.ListFood;
import com.javaclimb.cate.service.ListFoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 美食合集里面的美食service实现类
 */
@Service
public class ListFoodServiceImpl implements ListFoodService {
    @Autowired
    private ListFoodMapper listFoodMapper;

    /**
     * 增加
     *
     * @param listFood
     */
    @Override
    public boolean insert(ListFood listFood) {
        return listFoodMapper.insert(listFood) > 0;
    }

    /**
     * 修改
     *
     * @param listFood
     */
    @Override
    public boolean update(ListFood listFood) {
        return listFoodMapper.update(listFood) > 0;
    }

    /**
     * 删除
     *
     * @param id
     */
    @Override
    public boolean delete(Integer id) {
        return listFoodMapper.delete(id) > 0;
    }

    /**
     * 根据美食id和美食合集id删除
     */
    @Override
    public boolean deleteByFoodIdAndFoodListId(Integer foodId, Integer foodListId) {
        return listFoodMapper.deleteByFoodIdAndFoodListId(foodId, foodListId) > 0;
    }

    /**
     * 根据主键查询整个对象
     *
     * @param id
     */
    @Override
    public ListFood selectByPrimaryKey(Integer id) {
        return listFoodMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询所有美食合集里面的美食
     */
    @Override
    public List<ListFood> allListFood() {
        return listFoodMapper.allListFood();
    }

    /**
     * 根据美食合集id查询所有的美食
     *
     * @param foodListId
     */
    @Override
    public List<ListFood> listFoodOfFoodListId(Integer foodListId) {
        return listFoodMapper.listFoodOfFoodListId(foodListId);
    }

    /**
     * 根据 id 修改美食vip状态
     *
     * @param id:
     * @return int
     * @since 2023/3/3 10:59
     */
    @Override
    public int updVipStatusById(String id, Boolean isVip) {
        return listFoodMapper.updVipStatusById(id, isVip);
    }
}
