package com.javaclimb.cate.service.impl;

import com.javaclimb.cate.dao.MarkMapper;
import com.javaclimb.cate.domain.Mark;
import com.javaclimb.cate.service.MarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 评价service实现
 */
@Service
public class MarkServiceImpl implements MarkService {

    @Autowired
    private MarkMapper markMapper;

    /**
     * 增加
     *
     * @param mark
     */
    @Override
    public boolean insert(Mark mark) {
        return markMapper.insert(mark)>0;
    }

    /**
     * 查总分
     *
     * @param foodId
     */
    @Override
    public int selectFoodScoreSum(Integer foodId) {
        return markMapper.selectFoodScoreSum(foodId);
    }
    /**
     * 查总分
     *
     * @param foodListId
     */
    @Override
    public int selectFoodListScoreSum(Integer foodListId) {
        return markMapper.selectFoodListScoreSum(foodListId);
    }
    /**
     * 查总分
     *
     * @param foodWindowId
     */
    @Override
    public int selectFoodWindowScoreSum(Integer foodWindowId) {
        return markMapper.selectFoodWindowScoreSum(foodWindowId);
    }

    /**
     * 查总评分人数
     *
     * @param foodId
     */
    @Override
    public int selectFoodMarkNum(Integer foodId) {
        return markMapper.selectFoodMarkNum(foodId);
    }
    /**
     * 查总评分人数
     *
     * @param foodListId
     */
    @Override
    public int selectFoodListMarkNum(Integer foodListId) {
        return markMapper.selectFoodListMarkNum(foodListId);
    }
    /**
     * 查总评分人数
     *
     * @param foodWindowId
     */
    @Override
    public int selectFoodWindowMarkNum(Integer foodWindowId) {
        return markMapper.selectFoodWindowMarkNum(foodWindowId);
    }

    /**
     * 计算平均分
     *
     * @param foodId
     */
    @Override
    public int markOfFoodId(Integer foodId){
        int rankNum = markMapper.selectFoodMarkNum(foodId);
        if(rankNum==0){
            return 5;
        }
        return markMapper.selectFoodScoreSum(foodId)/rankNum;
    }
    /**
     * 计算平均分
     *
     * @param foodListId
     */
    @Override
    public int markOfFoodListId(Integer foodListId) {
        int rankNum = markMapper.selectFoodListMarkNum(foodListId);
        if(rankNum==0){
            return 5;
        }
        return markMapper.selectFoodListScoreSum(foodListId)/rankNum;
    }
    /**
     * 计算平均分
     *
     * @param foodWindowId
     */
    @Override
    public int markOfFoodWindowId(Integer foodWindowId){
        int rankNum = markMapper.selectFoodWindowMarkNum(foodWindowId);
        if(rankNum==0){
            return 5;
        }
        return markMapper.selectFoodWindowScoreSum(foodWindowId)/rankNum;
    }
}
