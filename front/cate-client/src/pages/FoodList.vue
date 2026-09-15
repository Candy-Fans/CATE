<template>
    <div class="food-list">
        <ul class="food-list-header"><!-- foodStyle合集类别，预设在了foodList.js里 -->
            <li v-for="(item,index) in foodStyle" :key="index" @click="handleChangeView(item.name)"
                :class="{active:item.name==activeName}"><!-- 点击相应类别切换内容 -->
                {{item.name}}<!-- 全部美食、饺子馄饨... -->
            </li>
        </ul>
        <div>
            <content-list :contentList="data"></content-list>
            <div class="pagination"><!-- elementUi的页码组件 -->
                <el-pagination @current-change="handleCurrentChange" background layout="total,prev,pager,next"
                        :current-page="currentPage" :page-size="pageSize" :total="albumDatas.length">
                </el-pagination>
            </div>
        </div>
    </div>
</template>
<script>
import ContentList from '../components/ContentList';
import {getAllFoodList,getFoodListOfLikeStyle} from '../api/index';
import {foodStyle} from '../assets/data/foodList';

export default {
    name: 'food-list',
    components:{
        ContentList
    },
    data(){
        return{
            albumDatas: [],      //美食合集数据
            pageSize: 10,        //页面大小，一页有10条数据
            currentPage: 1,      //当前页，默认第一页
            foodStyle: [],           //风格，全部美食、饺子馄饨...
            activeName: '全部美食'    //当前风格，默认是全部
        }
    },
    computed:{
        //计算当前表格中的数据
        data(){
            return this.albumDatas.slice((this.currentPage - 1)*this.pageSize,this.currentPage*this.pageSize)
        }
    },
    mounted(){
        this.foodStyle = foodStyle;
        this.getFoodList();
    },

    methods:{
        getFoodList(){            
            getAllFoodList()//获取全部美食合集
                .then(res =>{
                    this.currentPage = 1;
                    this.albumDatas = res;
                })           
        },
        //获取当前页
        handleCurrentChange(val){
            this.currentPage = val;
        },
        //根据style显示对应的美食合集
        handleChangeView(name){
            this.activeName = name;
            this.albumDatas = [];
            if(name == '全部美食'){
                this.getFoodList();
            }else{
                this.goFoodListOfStyle(name)
            }
        },
        //根据style查询对应的美食合集
        goFoodListOfStyle(style){
            getFoodListOfLikeStyle(style)
                .then(res =>{
                        this.currentPage = 1;
                        this.albumDatas = res;
                    }) 
        },

    }
}
</script>

<style lang="scss" scoped>
@import '../assets/css/food-list.scss';
</style>