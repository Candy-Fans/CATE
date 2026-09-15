<template><!-- 具体窗口页面 -->
    <div class="food-list-album">
      <div class="album-slide">
        <div class="album-img">
          <img :src="attachImageUrl(tempList.pic)">
        </div>
        <div class="album-info">
          <h2>简介：</h2>
          <span>
            {{ tempList.introduction }}
          </span>
        </div>
      </div>
      <div class="album-content">
        <div class="album-title">
          <p>{{ tempList.title }}</p>
        </div>
        <div class="album-score">
          <div>
            <h3>窗口评分：</h3>
            <div>
              <el-rate v-model="average" disabled></el-rate>
            </div>
          </div>
          <span>{{ average * 2 }}</span>
          <div>
            <h3>评价：</h3>
            <div @click="setMark">
              <el-rate v-model="mark" allow-half show-text></el-rate>
            </div>
          </div>
        </div>
        <div class="foods-body">
          <album-content :foodList="listOfFoods">
            <template slot="title">美食列表</template>
          </album-content>
          <comment :playId="foodWindowId" :type="2"></comment><!-- 评论 2表示对于窗口的评论 -->
        </div>
      </div>
    </div>
  </template>
  <script>
  import {mixin} from '../mixins'
  import {mapGetters} from 'vuex'
  import {setMark, getMarkOf} from '../api/index'
  import {foodOfFoodWindowId} from '../api/index';
  import AlbumContent from '../components/AlbumContent'
  import Comment from '../components/Comment'
  
  export default {
    name: 'foodWindow-album',
    mixins: [mixin],
    components: {
      AlbumContent,
      Comment
    },
    data () {
          return {
          foodWindowId: '',       //前面传来的窗口id
          foodWindow: {},         //当前窗口信息
			    sid: '',       //传来的美食id
          average: 0,         //平均分
          mark: 0,         //提交评价的分数
          typeS:2,
        }
    },
    computed: {
            ...mapGetters([
            'listOfFoods',      //当前美食列表
            'tempList',         //当前窗口对象
            'loginIn',          //用户是否已登录
            'userId',           //当前登录用户id
        ])
    },
    created () {
      this.foodWindowId = this.$route.params.id;//当前页面路由中的id值即当前美食窗口id
      this.foodWindow = this.tempList;//当前窗口对象
      this.getFoodOfFoodWindowId();//根据窗口id获取美食
      const sid = localStorage.getItem("sid")
      if (sid && sid !=="undefined") {
        this.sid = JSON.parse(sid)
      } else {
        this.sid = this.$route.params.sid

        localStorage.setItem("sid", JSON.stringify(this.$route.params.sid))
      }
        this.foodWindowId = this.$route.params.id
        this.getMark()
    },beforeDestroy () {
		  localStorage.removeItem("sid")
    },
  
    methods: {
      //根据窗口id查询美食
      getFoodOfFoodWindowId(){
            foodOfFoodWindowId(this.foodWindowId)
                .then(res => {
                  this.$store.commit('setListOfFoods',res);
                })
                .catch(err =>{ alert(this.foodWindowId);
                    console.log(err)
                })
        },
        //获取性别
      attachSex(value){
            if(value==0){
                return '东餐厅'
            }else if(value==1){
                return '西餐厅'
            }
            return ''
        },
      
      //获取美食合集评分
      getMark () {
        getMarkOf(this.typeS,this.foodWindowId)
          .then(res => {
            this.average = res / 2
          })
          .catch(err => {
            console.log(err)
          })
      },
      //提交评分
      setMark () {
          if (this.loginIn) {
            let params = new URLSearchParams();
            params.append('consumerId', this.userId)
            params.append('typeS',2);
            params.append('foodWindowId',this.foodWindowId);
            params.append('score', this.mark * 2)
            setMark(params)
              .then(res => {
                if (res.code == 1) {
                  this.notify('评分成功', 'success')
                  this.getMark()
                } else {
                  this.notify('评分失败', 'error')
                }
              })
              .catch(err => {
                this.notify('您已经评价过啦', 'error')
              })
          } else {
            this.mark = null
            this.notify('请先登录', 'warning')
          }
        }
      }
  }
  </script>
  
  <style lang="scss" scoped>
  @import '../assets/css/food-list-album.scss';
  </style>
  