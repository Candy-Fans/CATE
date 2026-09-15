package com.javaclimb.cate.controller;

import com.alibaba.fastjson.JSONObject;
import com.javaclimb.cate.domain.Mark;
import com.javaclimb.cate.service.MarkService;
import com.javaclimb.cate.utils.Consts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class MarkController {

    @Autowired
    private MarkService markService;

    /**
     * 新增评价
     */
    @RequestMapping(value = "/mark/add",method = RequestMethod.POST)
    public Object add(HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        String consumerId = request.getParameter("consumerId");//用户id
        String typeS =request.getParameter("typeS");//评论类型（0美食1美食合集2窗口）
        String foodId = request.getParameter("foodId");           //美食id
        String foodListId = request.getParameter("foodListId");//美食合集id
        String foodWindowId = request.getParameter("foodWindowId");   //窗口id
        String score = request.getParameter("score");//分数

        Mark mark = new Mark();
        mark.setConsumerId(Integer.parseInt(consumerId));
        mark.setTypeS(new Byte(typeS));
        if(new Byte(typeS) ==0){
            mark.setFoodId(Integer.parseInt(foodId));
        }else if(new Byte(typeS) ==1){
            mark.setFoodListId(Integer.parseInt(foodListId));
        }else {
            mark.setFoodWindowId(Integer.parseInt(foodWindowId));
        }
        mark.setScore(Integer.parseInt(score));

        boolean flag = markService.insert(mark);
        if(flag){
            jsonObject.put(Consts.CODE,1);
            jsonObject.put(Consts.MSG,"评价成功");
            return jsonObject;
        }
        jsonObject.put(Consts.CODE,0);
        jsonObject.put(Consts.MSG,"评价失败");
        return jsonObject;
    }

    /**
     * 计算平均分
     */
    @RequestMapping(value = "/scoreOfFood",method = RequestMethod.GET)
    public Object scoreOfFood(HttpServletRequest request){
        String foodId = request.getParameter("foodId");
        return markService.markOfFoodId(Integer.parseInt(foodId));
    }
    /**
     * 计算平均分
     */
    @RequestMapping(value = "/scoreOfFoodList",method = RequestMethod.GET)
    public Object scoreOfFoodList(HttpServletRequest request){
        String foodListId = request.getParameter("foodListId");
        return markService.markOfFoodListId(Integer.parseInt(foodListId));
    }
    /**
     * 计算平均分
     */
    @RequestMapping(value = "/scoreOfFoodWindow",method = RequestMethod.GET)
    public Object scoreOfFoodWindow(HttpServletRequest request){
        String foodWindowId = request.getParameter("foodWindowId");
        return markService.markOfFoodWindowId(Integer.parseInt(foodWindowId));
    }

}






















