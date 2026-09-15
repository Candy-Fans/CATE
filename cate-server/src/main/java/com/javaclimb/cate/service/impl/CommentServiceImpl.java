package com.javaclimb.cate.service.impl;

import com.javaclimb.cate.dao.CommentMapper;
import com.javaclimb.cate.domain.Comment;
import com.javaclimb.cate.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 评论service实现类
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;
    /**
     * 增加
     *
     * @param comment
     */
    @Override
    public boolean insert(Comment comment) {
        return commentMapper.insert(comment)>0;
    }

    /**
     * 修改
     *
     * @param comment
     */
    @Override
    public boolean update(Comment comment) {
        return commentMapper.update(comment)>0;
    }

    /**
     * 删除
     *
     * @param id
     */
    @Override
    public boolean delete(Integer id) {
        return commentMapper.delete(id)>0;
    }

    /**
     * 根据主键查询整个对象
     *
     * @param id
     */
    @Override
    public Comment selectByPrimaryKey(Integer id) {
        return commentMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询所有评论
     */
    @Override
    public List<Comment> allComment() {
        return commentMapper.allComment();
    }

    /**
     * 查询某个美食下的所有评论
     *
     * @param foodId
     */
    @Override
    public List<Comment> commentOfFoodId(Integer foodId) {
        return commentMapper.commentOfFoodId(foodId);
    }

    /**
     * 查询某个美食合集下的所有评论
     *
     * @param foodListId
     */
    @Override
    public List<Comment> commentOfFoodListId(Integer foodListId) {
        return commentMapper.commentOfFoodListId(foodListId);
    }

    /**
     * 查询某个窗口下的所有评论
     */
    @Override
    public List<Comment> commentOfFoodWindowId(Integer foodWindowId){
        return commentMapper.commentOfFoodWindowId(foodWindowId);
    }
}
