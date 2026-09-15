import axios from 'axios'
import router from '../router'

import {Notification} from 'element-ui'

axios.defaults.timeout = 5000  //超市时间是5秒
axios.defaults.withCredentials = true  //允许跨域
//Content-Type 响应头
axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded;charset=UTF-8'
//基础url
axios.defaults.baseURL = 'http://localhost:8888'

//响应拦截器
axios.interceptors.response.use(
  response => {
    //如果reponse里面的status是200，说明访问到接口了，否则错误
    if (response.status === 200) {
      //判断有没有cookie
      let cookie = document.cookie
      //包含 cookie 就正常跳转,否则跳转到登录页面
      if (!cookie.includes('satoken')) {
        toLoinPage()
      }
      return Promise.resolve(response)
    } else {
      return Promise.reject(response)
    }

  },
  error => {
    if (error.response.status) {
      switch (error.response.status) {
        case 401:       //未登录
          toLoinPage()
          break
        case 404:   //没找到
          break
      }
      return Promise.reject(error.response)
    }
  }
)

/**
 * 封装get方法
 */
function get (url, params = {}) {
  return new Promise((resolve, reject) => {
    axios.get(url, {params: params})
      .then(response => {
        if (!response) return
        resolve(response.data)
      })
      .catch(err => {
        reject(err)
      })
  })
}

/**
 * 封装post方法
 */
function post (url, data = {}) {
  return new Promise((resolve, reject) => {
    axios.post(url, data)
      .then(response => {
        if (!response) return
        resolve(response.data)
      })
      .catch(err => {
        reject(err)
      })
  })
}

// 跳转到登录页面
function toLoinPage () {
  //获取当前地址栏
  let href = location.href
  href = href.split('?')
  //如果不是在登录页面,就提示这个消息  如果在登录页面就不提示
  if (!href[0].endsWith('/#/')) {
    Notification.error({
      title: '登录已过期或未登录，请重新登录',
    })
  }
  router.replace({
    path: '/',
    query: {
      redirect: router.currentRoute.fullPath
    }
  })
}

export {
  get,
  post
}
