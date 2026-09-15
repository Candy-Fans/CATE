import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      component: resolve => require(['../pages/Login.vue'], resolve)
    },

    {
      path: '/Home',
      component: resolve => require(['../components/Home.vue'], resolve),
      children: [
        {
          path: '/Info',
          component: resolve => require(['../pages/InfoPage.vue'], resolve)
        },
        {
          path: '/Consumer',
          component: resolve => require(['../pages/ConsumerPage.vue'], resolve)
        },
        {
          path: '/FoodWindow',
          component: resolve => require(['../pages/FoodWindowPage.vue'], resolve)
        },
        {
          path: '/FoodList',
          component: resolve => require(['../pages/FoodListPage.vue'], resolve)
        },
        {
          path: '/Food',
          component: resolve => require(['../pages/FoodPage.vue'], resolve)
        },
        {
          path: '/ListFood',
          component: resolve => require(['../pages/ListFoodPage.vue'], resolve)
        },
        {
          path: '/Collect',
          component: resolve => require(['../pages/CollectPage.vue'], resolve)
        },
        {
          path: '/Comment',
          component: resolve => require(['../pages/CommentPage.vue'], resolve)
        }

      ]
    }

  ]
})
