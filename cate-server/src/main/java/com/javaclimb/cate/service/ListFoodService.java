package com.javaclimb.cate.service;

import com.javaclimb.cate.domain.ListFood;

import java.util.List;

/**
 * 美食合集里面的美食service接口
 */
public interface ListFoodService {
    /**
     * 增加
     */
    public boolean insert(ListFood listFood);

    /**
     * 修改
     */
    public boolean update(ListFood listFood);

    /**
     * 删除
     */
    public boolean delete(Integer id);

    /**
     * 根据美食id和美食合集id删除
     */
    public boolean deleteByFoodIdAndFoodListId(Integer foodId, Integer foodListId);

    /**
     * 根据主键查询整个对象
     */
    public ListFood selectByPrimaryKey(Integer id);

    /**
     * 查询所有美食合集里面的美食
     */
    public List<ListFood> allListFood();

    /**
     * 根据美食合集id查询所有的美食
     */
    public List<ListFood> listFoodOfFoodListId(Integer foodListId);

    /**
     * 根据 id 修改美食vip状态
     *
     * @param id:
     * @return int
     * @since 2023/3/3 10:59
     */
    int updVipStatusById(String id, Boolean isVip);
}
