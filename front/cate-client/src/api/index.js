import Axios from 'axios'
import {get, post} from './http'

//============窗口相关================
//查询窗口
export const getAllFoodWindow = () => get(`foodWindow/allFoodWindow`)
//根据东西餐厅查询窗口
export const getFoodWindowOfSex = (sex) => get(`foodWindow/foodWindowOfSex?sex=${sex}`)

//============美食相关================
//根据窗口id查询美食
export const foodOfFoodWindowId = (id) => get(`food/foodWindow/detail?foodWindowId=${id}`)
//根据美食id查询美食对象
export const foodOfFoodId = (id) => get(`food/detail?foodId=${id}`)
//根据窗口名字模糊查询美食
export const likeFoodOfName = (keywords) => get(`food/likeFoodOfName?foodName=${keywords}`)
//根据美食id增加美食点击次数
export const addFoodNums = (id) => get(`food/addNums?foodId=${id}`)
//查询美食合集
export const topFood = () => get(`food/topFood`)
//查询推荐美食
export const topRecommend = (userId) => get(`/collect/topRecommendOfUserId?userId=${userId}`)

//============美食合集相关================
//查询美食合集
export const getAllFoodList = () => get(`foodList/allFoodList`)
//返回标题包含文字的美食合集列表
export const getFoodListOfLikeTitle = (keywords) => get(`foodList/likeTitle?title=${keywords}`)
//根据风格模糊查询美食合集列表
export const getFoodListOfLikeStyle = (style) => get(`foodList/likeStyle?style=${style}`)

//============美食合集的美食相关============
//根据美食合集id查询美食列表
export const listFoodDetail = (foodListId) => get(`listFood/detail?foodListId=${foodListId}`)

//============用户相关================
//查询用户
export const getAllConsumer = () => get(`consumer/allConsumer`)
//注册
export const SignUp = (params) => post(`/consumer/add`, params)
//登录
export const loginIn = (params) => post(`/consumer/login`, params)
//根据用户id查询该用户的详细信息
export const getUserOfId = (id) => get(`/consumer/selectByPrimaryKey?id=${id}`)
//更新用户信息
export const updateUserMsg = (params) => post(`/consumer/update`, params)
//退出登录
export  const  logout = ()=>post(`/consumer/logout`)



//===========评价======================
//提交评分
export const setMark = (params) => post(`/mark/add`, params)
//获取指定美食合集的平均分
export const getMarkOf = (typeS, id) => {
  if (typeS == 0) {              //美食
    return get(`/scoreOfFood?foodId=${id}`)
  } else if (typeS == 1){                      //美食合集
    return get(`/scoreOfFoodList?foodListId=${id}`)
  } else{
    return get(`/scoreOfFoodWindow?foodWindowId=${id}`)//窗口
  }
}

//===========评论======================
//提交评论
export const setComment = (params) => post(`/comment/add`, params)
//点赞
export const setLike = (params) => post(`/comment/like`, params)
//返回当前美食合集或美食的评论列表
export const getAllComment = (type, id) => {
  if (type == 0) {              //美食
    return get(`/comment/commentOfFoodId?foodId=${id}`)
  } else if (type == 1){                      //美食合集
    return get(`/comment/commentOfFoodListId?foodListId=${id}`)
  } else{
    return get(`/comment/commentOfFoodWindowId?foodWindowId=${id}`)//窗口
  }
}

//===============收藏===================
//新增收藏
export const setCollect = (params) => post(`/collect/add`, params)
//指定用户的收藏列表
export const getCollectOfUserId = (userId) => get(`/collect/collectOfUserId?userId=${userId}`)

//随机按钮
export const getRandomFood = () => get(`food/randomFood`)
