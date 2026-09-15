package com.javaclimb.cate.controller;

import com.alibaba.fastjson.JSONObject;
import com.javaclimb.cate.domain.Comment;
import com.javaclimb.cate.service.CommentService;
import com.javaclimb.cate.utils.Consts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 评论控制类
 */
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    /**
     * 添加评论
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Object addComment(HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        String userId = request.getParameter("userId");           //用户id
        String type = request.getParameter("type");               //评论类型（0美食1美食合集2窗口）
        String foodId = request.getParameter("foodId");           //美食id
        String foodListId = request.getParameter("foodListId");   //美食合集id
        String foodWindowId = request.getParameter("foodWindowId");   //窗口id
        String content = request.getParameter("content").trim();         //评论内容

        //保存到评论的对象中
        Comment comment = new Comment();
        comment.setUserId(Integer.parseInt(userId));
        comment.setType(new Byte(type));
        if(new Byte(type) ==0){
            comment.setFoodId(Integer.parseInt(foodId));
        }else if(new Byte(type) ==1){
            comment.setFoodListId(Integer.parseInt(foodListId));
        }else {
            comment.setFoodWindowId(Integer.parseInt(foodWindowId));
        }
        comment.setContent(content);
        boolean flag = commentService.insert(comment);
        if(flag){   //保存成功
            jsonObject.put(Consts.CODE,1);
            jsonObject.put(Consts.MSG,"评论成功");
            return jsonObject;
        }
        jsonObject.put(Consts.CODE,0);
        jsonObject.put(Consts.MSG,"评论失败");
        return jsonObject;
    }

    /**
     * 修改评论
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public Object updateComment(HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        String id = request.getParameter("id").trim();                   //主键
        String userId = request.getParameter("userId").trim();           //用户id
        String type = request.getParameter("type").trim();               //评论类型（0美食1美食合集）
        String foodId = request.getParameter("foodId").trim();           //美食id
        String foodListId = request.getParameter("foodListId").trim();   //美食合集id
        String foodWindowId = request.getParameter("foodWindowId").trim();   //窗口id
        String content = request.getParameter("content").trim();         //评论内容

        //保存到评论的对象中
        Comment comment = new Comment();
        comment.setId(Integer.parseInt(id));
        comment.setUserId(Integer.parseInt(userId));
        comment.setType(new Byte(type));
        if(foodId!=null&&foodId.equals("")){
            foodId = null;
        }else {
            comment.setFoodId(Integer.parseInt(foodId));
        }
        if(foodListId!=null&&foodListId.equals("")){
            foodListId = null;
        }else {
            comment.setFoodListId(Integer.parseInt(foodListId));
        }
        if(foodWindowId!=null&&foodWindowId.equals("")){
            foodWindowId = null;
        }else {
            comment.setFoodWindowId(Integer.parseInt(foodWindowId));
        }
        comment.setContent(content);

        boolean flag = commentService.update(comment);
        if(flag){   //保存成功
            jsonObject.put(Consts.CODE,1);
            jsonObject.put(Consts.MSG,"修改成功");
            return jsonObject;
        }
        jsonObject.put(Consts.CODE,0);
        jsonObject.put(Consts.MSG,"修改失败");
        return jsonObject;
    }

    /**
     * 删除评论
     */
    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    public Object deleteComment(HttpServletRequest request){
        String id = request.getParameter("id").trim();          //主键
        boolean flag = commentService.delete(Integer.parseInt(id));
        return flag;
    }

    /**
     * 根据主键查询整个对象
     */
    @RequestMapping(value = "/selectByPrimaryKey",method = RequestMethod.GET)
    public Object selectByPrimaryKey(HttpServletRequest request){
        String id = request.getParameter("id").trim();          //主键
        return commentService.selectByPrimaryKey(Integer.parseInt(id));
    }

    /**
     * 查询所有评论
     */
    @RequestMapping(value = "/allComment",method = RequestMethod.GET)
    public Object allComment(HttpServletRequest request){
        return commentService.allComment();
    }

    /**
     * 查询某个美食下的所有评论
     */
    @RequestMapping(value = "/commentOfFoodId",method = RequestMethod.GET)
    public Object commentOfFoodId(HttpServletRequest request){
        String foodId = request.getParameter("foodId");          //美食id
        return commentService.commentOfFoodId(Integer.parseInt(foodId));
    }

    /**
     * 查询某个美食合集下的所有评论
     */
    @RequestMapping(value = "/commentOfFoodListId",method = RequestMethod.GET)
    public Object commentOfFoodListId(HttpServletRequest request){
        String foodListId = request.getParameter("foodListId");          //美食id
        return commentService.commentOfFoodListId(Integer.parseInt(foodListId));
    }
    /**
     * 查询某个窗口的所有评论
     */
    @RequestMapping(value = "/commentOfFoodWindowId",method = RequestMethod.GET)
    public Object commentOfFoodWindowId(HttpServletRequest request){
        String foodWindowId = request.getParameter("foodWindowId");          //美食id
        return commentService.commentOfFoodWindowId(Integer.parseInt(foodWindowId));
    }

    /**
     * 给某个评论点赞
     */
    @RequestMapping(value = "/like",method = RequestMethod.POST)
    public Object like(HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        String id = request.getParameter("id").trim();           //主键
        String up = request.getParameter("up").trim();           //用户id

        //保存到评论的对象中
        Comment comment = new Comment();
        comment.setId(Integer.parseInt(id));
        comment.setUp(Integer.parseInt(up));

        boolean flag = commentService.update(comment);
        if(flag){   //保存成功
            jsonObject.put(Consts.CODE,1);
            jsonObject.put(Consts.MSG,"点赞成功");
            return jsonObject;
        }
        jsonObject.put(Consts.CODE,0);
        jsonObject.put(Consts.MSG,"点赞失败");
        return jsonObject;
    }

}






















