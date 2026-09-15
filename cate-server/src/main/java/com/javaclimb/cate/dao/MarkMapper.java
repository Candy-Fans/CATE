package com.javaclimb.cate.dao;

import com.javaclimb.cate.domain.Mark;
import org.springframework.stereotype.Repository;

@Repository
public interface MarkMapper {
    /**
     *增加
     */
    public int insert(Mark mark);

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
}
