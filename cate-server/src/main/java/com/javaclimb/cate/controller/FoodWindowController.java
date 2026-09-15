package com.javaclimb.cate.controller;

import com.alibaba.fastjson.JSONObject;
import com.javaclimb.cate.domain.FoodWindow;
import com.javaclimb.cate.service.FoodWindowService;
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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 窗口控制类
 */
@RestController
@RequestMapping("/foodWindow")
public class FoodWindowController {

    @Autowired
    private FoodWindowService foodWindowService;

    /**
     * 添加窗口
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Object addFoodWindow(HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        String name = request.getParameter("name").trim();      //窗口名
        String sex = request.getParameter("sex").trim();        //所属餐厅
        String pic = request.getParameter("pic").trim();        //头像
        String introduction = request.getParameter("introduction").trim();//简介
//        String birth = request.getParameter("birth").trim();    //生日
//        String location = request.getParameter("location").trim();//地区
//        //把生日转换成Date格式
//        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        Date birthDate = new Date();
//        try {
//            birthDate = dateFormat.parse(birth);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
        //保存到窗口的对象中
        FoodWindow foodWindow = new FoodWindow();
        foodWindow.setName(name);
        foodWindow.setSex(new Byte(sex));
        foodWindow.setPic(pic);
        foodWindow.setIntroduction(introduction);
        boolean flag = foodWindowService.insert(foodWindow);
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
     * 修改窗口
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public Object updateFoodWindow(HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        String id = request.getParameter("id").trim();          //主键
        String name = request.getParameter("name").trim();      //姓名
        String sex = request.getParameter("sex").trim();        //性别
        String introduction = request.getParameter("introduction").trim();//简介

        //保存到窗口的对象中
        FoodWindow foodWindow = new FoodWindow();
        foodWindow.setId(Integer.parseInt(id));
        foodWindow.setName(name);
        foodWindow.setSex(new Byte(sex));
        foodWindow.setIntroduction(introduction);
        boolean flag = foodWindowService.update(foodWindow);
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
     * 删除窗口
     */
    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    public Object deleteFoodWindow(HttpServletRequest request){
        String id = request.getParameter("id").trim();          //主键
        boolean flag = foodWindowService.delete(Integer.parseInt(id));
        return flag;
    }

    /**
     * 根据主键查询整个对象
     */
    @RequestMapping(value = "/selectByPrimaryKey",method = RequestMethod.GET)
    public Object selectByPrimaryKey(HttpServletRequest request){
        String id = request.getParameter("id").trim();          //主键
        return foodWindowService.selectByPrimaryKey(Integer.parseInt(id));
    }

    /**
     * 查询所有窗口
     */
    @RequestMapping(value = "/allFoodWindow",method = RequestMethod.GET)
    public Object allFoodWindow(HttpServletRequest request){

        System.out.println(foodWindowService.allFoodWindow());
        return foodWindowService.allFoodWindow();
    }

    /**
     * 根据窗口名字模糊查询列表
     */
    @RequestMapping(value = "/foodWindowOfName",method = RequestMethod.GET)
    public Object foodWindowOfName(HttpServletRequest request){
        String name = request.getParameter("name").trim();          //窗口名字
        return foodWindowService.foodWindowOfName("%"+name+"%");
    }

    /**
     * 根据性别查询
     */
    @RequestMapping(value = "/foodWindowOfSex",method = RequestMethod.GET)
    public Object foodWindowOfSex(HttpServletRequest request){
        String sex = request.getParameter("sex").trim();          //性别
        return foodWindowService.foodWindowOfSex(Integer.parseInt(sex));
    }

    /**
     * 更新窗口图片
     */
    @RequestMapping(value = "/updateFoodWindowPic",method = RequestMethod.POST)
    public Object updateFoodWindowPic(@RequestParam("file") MultipartFile avatorFile, @RequestParam("id")int id){
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
                +System.getProperty("file.separator")+"foodWindowPic";
        //如果文件路径不存在，新增该路径
        File file1 = new File(filePath);
        if(!file1.exists()){
            file1.mkdir();
        }
        //实际的文件地址
        File dest = new File(filePath+System.getProperty("file.separator")+fileName);
        //存储到数据库里的相对文件地址
        String storeAvatorPath = "/img/foodWindowPic/"+fileName;
        try {
            avatorFile.transferTo(dest);
            FoodWindow foodWindow = new FoodWindow();
            foodWindow.setId(id);
            foodWindow.setPic(storeAvatorPath);
            boolean flag = foodWindowService.update(foodWindow);
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






















