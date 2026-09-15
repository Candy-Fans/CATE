<template>
  <div id="app">
    <the-header /><!-- 顶栏 -->
    <router-view class="cate-content"/><!--占位符，渲染相应路由的组件-->
    <scroll-top/>  <!-- 点击回到顶部 -->
  </div>
</template>

<script>
import TheHeader from './components/TheHeader';
import ScrollTop from './components/ScrollTop';
export default {
  name: 'App',
  components: {
    TheHeader,
    ScrollTop,
  }
}
</script>

<style  lang="scss" scoped>
@import './assets/css/app.scss';
</style>
