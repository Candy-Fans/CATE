<template>
    <div class="foodWindow">
        <ul class="foodWindow-header">
            <li v-for="(item,index) in foodWindowStyle" :key="index" @click="handleChangeView(item)"
                :class="{active:item.name==activeName}">
                {{item.name}}
            </li>
        </ul>
        <div>
            <content-list :contentList="data"></content-list>
            <div class="pagination">
                <el-pagination @current-change="handleCurrentChange" background layout="total,prev,pager,next"
                        :current-page="currentPage" :page-size="pageSize" :total="albumDatas.length">
                </el-pagination>
            </div>
        </div>
    </div>
</template>
<script>
import ContentList from '../components/ContentList';
import {getAllFoodWindow,getFoodWindowOfSex} from '../api/index';
import {foodWindowStyle} from '../assets/data/foodWindow';

export default {
    name: 'foodWindow',
    components:{
        ContentList
    },
    data(){
        return{
            albumDatas: [],      //窗口数据
            pageSize: 10,        //页面大小，一页有10条数据
            currentPage: 1,      //当前页，默认第一页
            foodWindowStyle: [],           //类型全部窗口、东餐厅、西餐厅
            activeName: '全部窗口'    //当前页面
        }
    },
    computed:{
        //计算当前表格中的数据
        data(){
            return this.albumDatas.slice((this.currentPage - 1)*this.pageSize,this.currentPage*this.pageSize)
        }
    },
    mounted(){
        this.foodWindowStyle = foodWindowStyle;//将foodWindow.js中的foodwindowstyle赋给当前页面的fws
        this.getFoodWindowList();//获取美食窗口列表
    },

    methods:{
        getFoodWindowList(){            
            getAllFoodWindow()//将获取到的美食窗口列表放入albumDatas展示出来
                .then(res =>{
                    this.currentPage = 1;
                    this.albumDatas = res;
                })           
        },
        //获取当前页
        handleCurrentChange(val){
            this.currentPage = val;
        },
        //根据类型显示对应的窗口
        handleChangeView(item){
            this.activeName = item.name;
            this.albumDatas = [];
            if(item.name == '全部窗口'){
                this.getFoodWindowList();
            }else{
                this.getFoodWindowOfSex(item.type)
            }
        },
        //根据类别查询对应的窗口
        getFoodWindowOfSex(sex){
            getFoodWindowOfSex(sex)
                .then(res =>{
                        this.currentPage = 1;
                        this.albumDatas = res;
                    }) 
        },
    }
}
</script>

<style lang="scss" scoped>
@import '../assets/css/foodWindow.scss';
</style>