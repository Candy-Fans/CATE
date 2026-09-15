package com.javaclimb.cate.dao;

import com.javaclimb.cate.domain.Collect;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 收藏Dao
 */
@Repository
public interface CollectMapper {
    /**
     *增加
     */
    public int insert(Collect collect);

    /**
     * 删除
     */
    public int delete(Integer id);

    /**
     * 根据用户id和美食id删除
     */
    public int deleteByUserIdFoodId(@Param("userId") Integer userId, @Param("foodId") Integer foodId);

    /**
     * 查询所有收藏
     */
    public List<Collect> allCollect();

    /**
     * 查询某个用户的收藏列表
     */
    public List<Collect> collectOfUserId(Integer userId);

    /**
     * 查询某个用户是否已经收藏了某个美食
     */
    public int existFoodId(@Param("userId") Integer userId, @Param("foodId") Integer foodId);
}
















