package com.javaclimb.cate.service;

import com.javaclimb.cate.domain.Comment;

import java.util.List;

/**
 * 评论service接口
 */
public interface CommentService {
    /**
     *增加
     */
    public boolean insert(Comment comment);

    /**
     *修改
     */
    public boolean update(Comment comment);

    /**
     * 删除
     */
    public boolean delete(Integer id);

    /**
     * 根据主键查询整个对象
     */
    public Comment selectByPrimaryKey(Integer id);

    /**
     * 查询所有评论
     */
    public List<Comment> allComment();

    /**
     * 查询某个美食下的所有评论
     */
    public List<Comment> commentOfFoodId(Integer foodId);

    /**
     * 查询某个美食合集下的所有评论
     */
    public List<Comment> commentOfFoodListId(Integer foodListId);
    /**
     * 查询某个窗口下的所有评论
     */
    public List<Comment> commentOfFoodWindowId(Integer foodWindowId);
}
