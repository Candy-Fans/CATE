package com.javaclimb.cate.controller;

import com.alibaba.fastjson.JSONObject;
import com.javaclimb.cate.domain.FoodList;
import com.javaclimb.cate.service.FoodListService;
import com.javaclimb.cate.utils.Consts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

/**
 * 美食合集控制类
 */
@RestController
@RequestMapping("/foodList")
public class FoodListController {

    @Autowired
    private FoodListService foodListService;

    /**
     * 添加美食合集
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Object addFoodList(HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        String title = request.getParameter("title").trim();      //标题
        String pic = request.getParameter("pic").trim();        //美食合集图片
        String introduction = request.getParameter("introduction").trim();//简介
        String style = request.getParameter("style").trim();    //风格

        //保存到美食合集的对象中
        FoodList foodList = new FoodList();
        foodList.setTitle(title);
        foodList.setPic(pic);
        foodList.setIntroduction(introduction);
        foodList.setStyle(style);
        boolean flag = foodListService.insert(foodList);
        if(flag){   //保存成功
            jsonObject.put(Consts.CODE,1);
            jsonObject.put(Consts.MSG,"添加成功");
            return jsonObject;
        }
        jsonObject.put(Consts.CODE,0);
        jsonObject.put(Consts.MSG,"添加失败");
        return jsonObject;
    }

    /**
     * 修改美食合集
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public Object updateFoodList(HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        String id = request.getParameter("id").trim();          //主键
        String title = request.getParameter("title").trim();      //标题
        String introduction = request.getParameter("introduction").trim();//简介
        String style = request.getParameter("style").trim();    //风格
        //保存到美食合集的对象中
        FoodList foodList = new FoodList();
        foodList.setId(Integer.parseInt(id));
        foodList.setTitle(title);
        foodList.setIntroduction(introduction);
        foodList.setStyle(style);
        boolean flag = foodListService.update(foodList);
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
     * 删除美食合集
     */
    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    public Object deleteFoodList(HttpServletRequest request){
        String id = request.getParameter("id").trim();          //主键
        boolean flag = foodListService.delete(Integer.parseInt(id));
        return flag;
    }

    /**
     * 根据主键查询整个对象
     */
    @RequestMapping(value = "/selectByPrimaryKey",method = RequestMethod.GET)
    public Object selectByPrimaryKey(HttpServletRequest request){
        String id = request.getParameter("id").trim();          //主键
        return foodListService.selectByPrimaryKey(Integer.parseInt(id));
    }

    /**
     * 查询所有美食合集
     */
    @RequestMapping(value = "/allFoodList",method = RequestMethod.GET)
    public Object allFoodList(HttpServletRequest request){
        return foodListService.allFoodList();
    }

    /**
     * 根据标题精确查询美食合集列表
     */
    @RequestMapping(value = "/foodListOfTitle",method = RequestMethod.GET)
    public Object foodListOfName(HttpServletRequest request){
        String title = request.getParameter("title").trim();          //美食合集标题
        return foodListService.foodListOfTitle(title);
    }

    /**
     * 根据标题模糊查询美食合集列表
     */
    @RequestMapping(value = "/likeTitle",method = RequestMethod.GET)
    public Object likeTitle(HttpServletRequest request){
        String title = request.getParameter("title").trim();          //美食合集标题
        return foodListService.likeTitle("%"+title+"%");
    }

    /**
     * 根据风格模糊查询美食合集列表
     */
    @RequestMapping(value = "/likeStyle",method = RequestMethod.GET)
    public Object likeStyle(HttpServletRequest request){
        String style = request.getParameter("style").trim();          //美食合集风格
        return foodListService.likeStyle("%"+style+"%");
    }

    /**
     * 更新美食合集图片
     */
    @RequestMapping(value = "/updateFoodListPic",method = RequestMethod.POST)
    public Object updateFoodListPic(@RequestParam("file") MultipartFile avatorFile, @RequestParam("id")int id){
        JSONObject jsonObject = new JSONObject();
        if(avatorFile.isEmpty()){
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"文件上传失败");
            return jsonObject;
        }
        //文件名=当前时间到毫秒+原来的文件名
        String fileName = System.currentTimeMillis()+avatorFile.getOriginalFilename();
        //文件路径
        String filePath = System.getProperty("user.dir")+System.getProperty("file.separator")+"img"
                +System.getProperty("file.separator")+"foodListPic";
        //如果文件路径不存在，新增该路径
        File file1 = new File(filePath);
        if(!file1.exists()){
            file1.mkdir();
        }
        //实际的文件地址
        File dest = new File(filePath+System.getProperty("file.separator")+fileName);
        //存储到数据库里的相对文件地址
        String storeAvatorPath = "/img/foodListPic/"+fileName;
        try {
            avatorFile.transferTo(dest);
            FoodList foodList = new FoodList();
            foodList.setId(id);
            foodList.setPic(storeAvatorPath);
            boolean flag = foodListService.update(foodList);
            if(flag){
                jsonObject.put(Consts.CODE,1);
                jsonObject.put(Consts.MSG,"上传成功");
                jsonObject.put("pic",storeAvatorPath);
                return jsonObject;
            }
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"上传失败");
            return jsonObject;
        } catch (IOException e) {
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"上传失败"+e.getMessage());
        }finally {
            return jsonObject;
        }
    }
}






















