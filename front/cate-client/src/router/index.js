import Vue from 'vue'
import Router from 'vue-router'
import Home from '@/pages/Home'
import MyMusic from '@/pages/MyCate'
import FoodWindow from '@/pages/FoodWindow'
import FoodList from '@/pages/FoodList'
import Search from '@/pages/Search'
import SignUp from '@/pages/SignUp'
import LoginIn from '@/pages/LoginIn'
import Setting from '@/pages/Setting'
import FoodWindowAlbum from '@/pages/FoodWindowAlbum'
import FoodListAlbum from '@/pages/FoodListAlbum'
import FoodDetail from '@/pages/FoodDetail'
Vue.use(Router)

export default new Router({
  routes: [    
    {
      path: '/',
      name: 'home',
      component: Home
    },
    {
      path: '/my-cate',
      name: 'my-cate',
      component: MyMusic
    },
    {
      path: '/foodWindow',
      name: 'foodWindow',
      component: FoodWindow
    },
    {
      path: '/food-list',
      name: 'food-list',
      component: FoodList
    },
    {
      path: '/search',
      name: 'search',
      component: Search
    },
    {
      path: '/sign-up',
      name: 'sign-up',
      component: SignUp
    },
    {
      path: '/login-in',
      name: 'login-in',
      component: LoginIn
    },
    {
      path: '/setting',
      name: 'setting',
      component: Setting
    },
    {
      path: '/foodWindow-album/:id',
      name: 'foodWindow-album',
      component: FoodWindowAlbum
    },
	  {
      path: '/foodWindow-album/:id/:sid',
      name: 'food-detail',
      component: FoodDetail
    },
    {
      path: '/food-list-album/:id',
      name: 'food-list-album',
      component: FoodListAlbum
    },    
  ],
  scrollBehavior (to, from, savedPosition) {
    return { x: 0, y: 0 }
  }
})
