package com.javaclimb.cate.dao;

import com.javaclimb.cate.domain.ListFood;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 美食合集里面的美食Dao
 */
@Repository
public interface ListFoodMapper {
    /**
     * 增加
     */
    public int insert(ListFood listFood);

    /**
     * 修改
     */
    public int update(ListFood listFood);

    /**
     * 删除
     */
    public int delete(Integer id);

    /**
     * 根据美食id和美食合集id删除
     */
    public int deleteByFoodIdAndFoodListId(@Param("foodId") Integer foodId, @Param("foodListId") Integer foodListId);

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
    int updVipStatusById(@Param("id") String id, @Param("isVip") Boolean isVip);
}
















