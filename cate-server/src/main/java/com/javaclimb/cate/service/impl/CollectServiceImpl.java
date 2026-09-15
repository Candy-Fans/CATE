package com.javaclimb.cate.service.impl;

import com.javaclimb.cate.dao.CollectMapper;
import com.javaclimb.cate.domain.Collect;
import com.javaclimb.cate.service.CollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 收藏service实现类
 */
@Service
public class CollectServiceImpl implements CollectService {

    @Autowired
    private CollectMapper collectMapper;


    /**
     * 增加
     *
     * @param collect
     */
    @Override
    public boolean insert(Collect collect) {
        return collectMapper.insert(collect)>0;
    }

    /**
     * 删除
     *
     * @param id
     */
    @Override
    public boolean delete(Integer id) {
        return collectMapper.delete(id)>0;
    }

    /**
     * 根据用户id和美食id删除
     *
     * @param userId
     * @param foodId
     */
    @Override
    public boolean deleteByUserIdFoodId(Integer userId, Integer foodId) {
        return collectMapper.deleteByUserIdFoodId(userId,foodId)>0;
    }

    /**
     * 查询所有收藏
     */
    @Override
    public List<Collect> allCollect() {
        return collectMapper.allCollect();
    }

    /**
     * 查询某个用户的收藏列表
     *
     * @param userId
     */
    @Override
    public List<Collect> collectOfUserId(Integer userId) {
        return collectMapper.collectOfUserId(userId);
    }

    /**
     * 查询某个用户是否已经收藏了某个美食
     *
     * @param userId
     * @param foodId
     */
    @Override
    public boolean existFoodId(Integer userId, Integer foodId) {
        return collectMapper.existFoodId(userId,foodId)>0;
    }

}
