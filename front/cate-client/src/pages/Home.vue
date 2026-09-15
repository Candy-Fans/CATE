<template>
  <div class="home"><!-- 主页 -->
    <swiper /> <!-- 轮播图 -->
      <div style="margin: auto;
        width: 50%;
        padding: 10px;">
      <el-button type="primary"  @click="getOneRandomFood();dialogTableVisible = true" 
      style="display:block;background: linear-gradient(to right, #ff4e50, #f9d423);
      color: #fff;margin:0 auto;font-size: large;"><!-- color是字的颜色 -->
      不知道吃什么?点我
      </el-button><!-- 从左到右颜色渐变的按钮 点击后 弹出对话框 随机弹出一个食物信息 -->
      <el-dialog title="来吃这个吧" :visible.sync="dialogTableVisible"><!-- 对话框，用来展示随机美食信息 -->
        <el-table :data="randomFood"><!-- 将随机的美食信息加载到表格当中 -->
          <el-table-column label="美食图片">
            <template slot-scope="scope">            
              <img :src="'http://localhost:8888'+scope.row.pic"  min-width="70" height="70" />
            </template> 
          </el-table-column>
          <el-table-column property="name" label="美食名称"></el-table-column>
          <el-table-column property="price" label="价格" width="50x"></el-table-column>
          <el-table-column>元</el-table-column>
        </el-table>
      </el-dialog>
    </div>
    <div class="section" v-for="(item,index) in foodsList" :key="index"><!-- 展示热榜、合集、窗口等信息 -->
      <div class="section-title">{{item.name}}</div>
      <content-list :contentList="item.list"></content-list>
    </div>
  </div>
</template>

<script>
import Swiper from "../components/Swiper";
import contentList from '../components/ContentList';
import {mapGetters} from 'vuex';
import {getAllFoodWindow,getAllFoodList,topFood,topRecommend,getRandomFood} from '../api/index';
export default {
  name: 'home',
  components: {
    Swiper,
    contentList
  },
  data () {
    return {
      foodsList: [
	      {name:"美食热榜",list: []},
        {name:"美食合集",list: []},
        {name:"美食窗口",list: []},
		    {name:"美食推荐",list: []}
      ],
      dialogTableVisible: false,//默认不显示随机食物的弹框
      randomFood: [
        {pic:"/img/foodPic/tubiao.jpg",name:"东二一兜鱼-炸鱼丸",price:6}
      ],//先在随机美食列表里放一条默认的美食信息，点击默认弹出此项，点击后替换成新的美食信息
    }
  },
  computed:{
        ...mapGetters([
            'userId',           //当前登录用户id
        ])
  },
  created () {
   this.getTopFood();
   this.getFoodList();
   this.getFoodWindow();
   this.getRecommend(this.userId);
  },
  methods: {
    getTopFood(){                      //获取点击次数前十条美食
      topFood().then((res) => {     
        this.foodsList[0].list = res.slice(0,10);//从0到9
      }).catch((err) => {
        console.log(err);
      })
    },
    getFoodList(){                      //获取前十条美食合集
      getAllFoodList().then((res) => {
        this.foodsList[1].list = res.slice(0,10);
      }).catch((err) => {
        console.log(err);
      })
    },
    getFoodWindow(){                      //获取前十个窗口
      getAllFoodWindow().then((res) => {
        this.foodsList[2].list = res.slice(0,10);
      }).catch((err) => {
        console.log(err);
      })
    },
    getRecommend(userId){                      //获取前五个推荐美食
      topRecommend(userId).then((res) => {
        this.foodsList[3].list = res;
      }).catch((err) => {
        console.log(err);
      })
    },
    getOneRandomFood(){             //获取随机美食
      getRandomFood()
        .then(res =>{   //获取到的随机美食的结果填充替换到randomFood的数据里
          this.randomFood.fill(res);
        })
        .catch(err => {
          console.log(err);
        })
    },
  }
}
</script>

<style lang="scss" scoped>
@import '../assets/css/home.scss';
</style>
