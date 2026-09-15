package com.javaclimb.cate.controller;

import cn.dev33.satoken.util.SaResult;
import com.alibaba.fastjson.JSONObject;
import com.javaclimb.cate.domain.ListFood;
import com.javaclimb.cate.service.ListFoodService;
import com.javaclimb.cate.utils.Consts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 美食合集的美食管理controller
 */
@RestController
@RequestMapping("/listFood")
public class ListFoodController {

    @Autowired
    private ListFoodService listFoodService;

    /**
     * 给美食合集添加美食
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Object addListFood(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        //获取前端传来的参数
        String foodId = request.getParameter("foodId").trim();  //美食id
        String foodListId = request.getParameter("foodListId").trim(); //美食合集id
        ListFood listFood = new ListFood();
        listFood.setFoodId(Integer.parseInt(foodId));
        listFood.setFoodListId(Integer.parseInt(foodListId));
        boolean flag = listFoodService.insert(listFood);
        if (flag) {
            jsonObject.put(Consts.CODE, 1);
            jsonObject.put(Consts.MSG, "保存成功");
            return jsonObject;
        }
        jsonObject.put(Consts.CODE, 0);
        jsonObject.put(Consts.MSG, "保存失败");
        return jsonObject;

    }

    /**
     * 根据美食合集id查询美食
     */
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public Object detail(HttpServletRequest request) {
        String foodListId = request.getParameter("foodListId");
        return listFoodService.listFoodOfFoodListId(Integer.parseInt(foodListId));
    }


    /**
     * 删除美食合集里的美食
     */
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public Object delete(HttpServletRequest request) {
        String foodId = request.getParameter("foodId").trim();                 //美食id
        String foodListId = request.getParameter("foodListId").trim();        //美食合集id
        boolean flag = listFoodService.deleteByFoodIdAndFoodListId(Integer.parseInt(foodId), Integer.parseInt(foodListId));
        return flag;
    }

    /**
     * 根据美食id 修改vip状态
     *
     * @param id:    美食ID
     * @param isVip: 是否是vip
     * @return cn.dev33.satoken.util.SaResult
     * @since 2023/3/3 13:41
     */
    @PostMapping("/change_vip_status/{id}/{isVip}")
    public SaResult updVipStatus(@PathVariable("id") String id, @PathVariable("isVip") Boolean isVip) {
        int rows = listFoodService.updVipStatusById(id, isVip);
        return rows != 0 ? SaResult.ok("成功修改美食状态") : SaResult.error("修改VIP状态失败");
    }


}




















