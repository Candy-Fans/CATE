package com.javaclimb.cate.service;

import com.javaclimb.cate.domain.Mark;

public interface MarkService {
    /**
     *增加
     */
    public boolean insert(Mark mark);

    /**
     * 查总分
     */
    public int selectFoodScoreSum(Integer foodId);
    public int selectFoodListScoreSum(Integer foodListId);
    public int selectFoodWindowScoreSum(Integer foodWindowId);

    /**
     * 查总评分人数
     */
    public int selectFoodMarkNum(Integer foodId);
    public int selectFoodListMarkNum(Integer foodListId);
    public int selectFoodWindowMarkNum(Integer foodWindowId);

    /**
     * 计算平均分
     */
    public int markOfFoodId(Integer foodId);
    public int markOfFoodListId(Integer foodListId);
    public int markOfFoodWindowId(Integer foodWindowId);
}
