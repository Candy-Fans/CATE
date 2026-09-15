package com.javaclimb.cate.controller;

import com.alibaba.fastjson.JSONObject;
import com.javaclimb.cate.domain.Food;
import com.javaclimb.cate.service.FoodService;
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
 * 美食管理controller
 */
@RestController
@RequestMapping("/food")
public class FoodController {

    @Autowired
    private FoodService foodService;

    /**
     * 添加美食
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Object addFood(HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        //获取前端传来的参数
        String foodWindowId = request.getParameter("foodWindowId").trim();  //所属窗口id
        String name = request.getParameter("name").trim();          //美食名
        String introduction = request.getParameter("introduction").trim();          //简介
        String price = request.getParameter("price").trim();          //价格
        String pic = "/img/foodPic/tubiao.jpg";                     //默认图片
            Food food = new Food();
            food.setFoodWindowId(Integer.parseInt(foodWindowId));
            food.setName(name);
            food.setIntroduction(introduction);
            food.setPic(pic);
            food.setPrice(Integer.parseInt(price));

            boolean flag = foodService.insert(food);
            if(flag){
                jsonObject.put(Consts.CODE,1);
                jsonObject.put(Consts.MSG,"保存成功");
                return jsonObject;
            }
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"保存失败");
            return jsonObject;
        }
    /**
     * 根据窗口id查询美食
     */
    @RequestMapping(value = "/foodWindow/detail",method = RequestMethod.GET)
    public Object foodOfFoodWindowId(HttpServletRequest request){
        String foodWindowId = request.getParameter("foodWindowId");
        return foodService.foodOfFoodWindowId(Integer.parseInt(foodWindowId));
    }

    /**
     * 修改美食
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public Object updateFood(HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        String id = request.getParameter("id").trim();          //主键
        String name = request.getParameter("name").trim();      //美食名
        String introduction = request.getParameter("introduction").trim();//简介
        String price = request.getParameter("price").trim();          //价格

        //保存到窗口的对象中
        Food food = new Food();
        food.setId(Integer.parseInt(id));
        food.setName(name);
        food.setIntroduction(introduction);
        food.setPrice(Integer.parseInt(price));
        boolean flag = foodService.update(food);
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
     * 删除美食
     */
    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    public Object deleteFoodWindow(HttpServletRequest request){
        //-TODO 先查询到数据库中对应的文件地址，删除掉它再进行下面的代码
        String id = request.getParameter("id").trim();          //主键
        boolean flag = foodService.delete(Integer.parseInt(id));
        return flag;
    }

    /**
     * 更新美食图片
     */
    @RequestMapping(value = "/updateFoodPic",method = RequestMethod.POST)
    public Object updateFoodPic(@RequestParam("file") MultipartFile avatorFile, @RequestParam("id")int id){
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
                +System.getProperty("file.separator")+"foodPic";
        //如果文件路径不存在，新增该路径
        File file1 = new File(filePath);
        if(!file1.exists()){
            file1.mkdir();
        }
        //实际的文件地址
        File dest = new File(filePath+System.getProperty("file.separator")+fileName);
        //存储到数据库里的相对文件地址
        String storeAvatorPath = "/img/foodPic/"+fileName;
        try {
            avatorFile.transferTo(dest);
            Food food = new Food();
            food.setId(id);
            food.setPic(storeAvatorPath);
            boolean flag = foodService.update(food);
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


    /**
     * 更新美食
     */
    @RequestMapping(value = "/updateFoodUrl",method = RequestMethod.POST)
    public Object updateFoodUrl(@RequestParam("file") MultipartFile avatorFile, @RequestParam("id")int id){
        JSONObject jsonObject = new JSONObject();
        if(avatorFile.isEmpty()){
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"文件上传失败");
            return jsonObject;
        }
        //文件名=当前时间到毫秒+原来的文件名
        String fileName = System.currentTimeMillis()+avatorFile.getOriginalFilename();
        //文件路径
        String filePath = System.getProperty("user.dir")+System.getProperty("file.separator")+"food";
        //如果文件路径不存在，新增该路径
        File file1 = new File(filePath);
        if(!file1.exists()){
            file1.mkdir();
        }
        //实际的文件地址
        File dest = new File(filePath+System.getProperty("file.separator")+fileName);
        //存储到数据库里的相对文件地址
        String storeAvatorPath = "/food/"+fileName;
        try {
            avatorFile.transferTo(dest);
            Food food = new Food();
            food.setId(id);
            boolean flag = foodService.update(food);
            if(flag){
                jsonObject.put(Consts.CODE,1);
                jsonObject.put(Consts.MSG,"上传成功");
                jsonObject.put("avator",storeAvatorPath);
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

    /**
     * 根据美食id查询美食对象
     */
    @RequestMapping(value = "/detail",method = RequestMethod.GET)
    public Object detail(HttpServletRequest request){
        String foodId = request.getParameter("foodId");
        return foodService.selectByPrimaryKey(Integer.parseInt(foodId));
    }



    /**
     * 根据美食id增加美食点击次数
     */
    @RequestMapping(value = "/addNums",method = RequestMethod.GET)
    public Object addNums(HttpServletRequest request){
        String foodId = request.getParameter("foodId");
        return foodService.addNums(Integer.parseInt(foodId));
    }

    /**
     * 根据窗口名字精确查询美食
     */
    @RequestMapping(value = "/foodOfFoodName",method = RequestMethod.GET)
    public Object foodOfFoodName(HttpServletRequest request){
        String foodName = request.getParameter("foodName");
        return foodService.foodOfName(foodName);
    }

    /**
     * 根据窗口名字模糊查询美食
     */
    @RequestMapping(value = "/likeFoodOfName",method = RequestMethod.GET)
    public Object likeFoodOfName(HttpServletRequest request){
        String foodName = request.getParameter("foodName");
        return foodService.likeFoodOfName(foodName);
    }

    /**
     * 查询所有美食
     */
    @RequestMapping(value = "/allFood",method = RequestMethod.GET)
    public Object allFood(HttpServletRequest request){
        return foodService.allFood();
    }

    /**
     * 查询所有美食
     */
    @RequestMapping(value = "/topFood",method = RequestMethod.GET)
    public Object topFood(HttpServletRequest request){
        return foodService.topFood();
    }
    /**
     * 随机id展示
     */
    @RequestMapping(value = "/randomFood",method = RequestMethod.GET)
    public Food  randomFood(){
        return foodService.randomFood();
    }
}




















