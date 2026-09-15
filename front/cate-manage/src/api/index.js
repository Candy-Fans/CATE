import {get, post} from './http'

// 判断管理员是否登录成功
export const getLoginStatus = (params) => post(`admin/login/status`, params)
export const loginWithPhoneNum = (phoneNum) => post(`admin/login/${phoneNum}`)

//============窗口相关================
//查询窗口
export const getAllFoodWindow = () => get(`foodWindow/allFoodWindow`)
//添加窗口
export const setFoodWindow = (params) => post(`foodWindow/add`, params)
//编辑窗口
export const updateFoodWindow = (params) => post(`foodWindow/update`, params)
//删除窗口
export const delFoodWindow = (id) => get(`foodWindow/delete?id=${id}`)

//============美食相关================
//根据窗口id查询美食
export const foodOfFoodWindowId = (id) => get(`food/foodWindow/detail?foodWindowId=${id}`)
//添加用户
export const setFood = (params) => post(`food/add`, params)
//编辑美食
export const updateFood = (params) => post(`food/update`, params)
//删除美食
export const delFood = (id) => get(`food/delete?id=${id}`)
//根据美食id查询美食对象
export const foodOfFoodId = (id) => get(`food/detail?foodId=${id}`)
//根据美食名获取美食对象
export const foodOfFoodName = (foodName) => get(`food/foodOfFoodName?foodName=${foodName}`)
//查询所有美食
export const allFood = () => get(`food/allFood`)
// //按照美食的id改变美食的vip状态
// export const changeVipStatus = ({
//   id,
//   isVip
// }) => post(`/listFood/change_vip_status/${id}/${isVip}`)

//============美食合集相关================
//查询美食合集
export const getAllFoodList = () => get(`foodList/allFoodList`)
//添加美食合集
export const setFoodList = (params) => post(`foodList/add`, params)
//编辑美食合集
export const updateFoodList = (params) => post(`foodList/update`, params)
//删除美食合集
export const delFoodList = (id) => get(`foodList/delete?id=${id}`)

//============美食合集的美食相关============
//根据美食合集id查询美食列表
export const listFoodDetail = (foodListId) => get(`listFood/detail?foodListId=${foodListId}`)
//给美食合集增加美食
export const listFoodAdd = (params) => post(`listFood/add`, params)
//删除美食合集的美食
export const delListFood = (foodId, foodListId) => get(`listFood/delete?foodId=${foodId}&foodListId=${foodListId}`)

//============用户相关================
//查询用户
export const getAllConsumer = () => get(`consumer/allConsumer`)
//添加用户
export const setConsumer = (params) => post(`consumer/add`, params)
//编辑用户
export const updateConsumer = (params) => post(`consumer/update`, params)
//删除用户
export const delConsumer = (id) => get(`consumer/delete?id=${id}`)
//根据用户id查询该用户的详细信息
export const getUserOfId = (id) => get(`/consumer/selectByPrimaryKey?id=${id}`)
// //修改会员状态
// export const updVipStatus = ({
//   id,
//   isVipUser
// }) => post(`/consumer/upd_vip_status/${id}/${isVipUser}`)

//===============收藏===================
//指定用户的收藏列表
export const getCollectOfUserId = (userId) => get(`/collect/collectOfUserId?userId=${userId}`)
//删除用户收藏的美食
export const deleteCollection = (userId, foodId) => get(`collect/delete?userId=${userId}&foodId=${foodId}`)

//===============评论===================
//指定美食合集的评论列表
export const getAllComment = (id, type) => {
  if (type == 0) {              //美食
    return get(`/comment/commentOfFoodId?foodId=${id}`)
  } else if (type == 1){                      //美食合集
    return get(`/comment/commentOfFoodListId?foodListId=${id}`)
  } else{
    return get(`/comment/commentOfFoodWindowId?foodWindowId=${id}`)//窗口
  }
}

//删除评论
export const deleteComment = (id) => get(`comment/delete?id=${id}`)

