<template>
    <div class="search">
        <nav class="searchList-nav" ref="change">
            <span :class="{isActive: toggle=='Foods'}" @click="handleChangeView('Foods')">美食名称</span>
            <span :class="{isActive: toggle=='FoodLists'}" @click="handleChangeView('FoodLists')">美食合集</span>
        </nav>
        <component :is="currentView"></component>
    </div>
</template>
<script>
import searchFoods from '../components/search/SearchFoods'
import searchFoodLists from '../components/search/SearchFoodLists'

export default {
   name: 'search',
   components: {
       searchFoods,
       searchFoodLists
   },
   data(){
       return {
           toggle: 'Foods',
           currentView: 'searchFoods'
       }
   },
   methods:{
       //切换组件
       handleChangeView(component){
           this.currentView = 'search'+component;
           this.toggle = component;
       }
   } 
}
</script>
<style lang="scss" scoped>
@import '../assets/css/search.scss';
</style>