<template>
    <div class="food-list-album"><!-- 详情页面左侧显示的美食图片、价格、简介 -->
      <div class="album-slide">
        <div class="album-img">
          <img :src="attachImageUrl(tempList.pic)">
        </div>
        <div class="album-info">
          <h2>价格：{{ tempList.price }}元</h2>
          <h2>简介：</h2>
          <span>
            {{ tempList.introduction }}
          </span>
        </div>
      </div>
      <div class="album-content">
        <div class="album-title">
          <p>{{ tempList.name }}</p>
          <div class="item" @click="collection"><!-- 点击爱心按钮收藏 -->
            <svg :class="{active:isActive}" class="iconC" style="height: 40px;width: 60;">
              <use xlink:href="#icon-xihuan-shi"></use>
            </svg>
          </div>
        </div>
        <div class="album-score"><!-- 分数板块 -->
          <div>
            <h3>美食评分：</h3>
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
        <div class="foods-body"><!--评论板块 -->
          <comment :playId="foodId" :type="0"></comment>
        </div>
      </div>
    </div>
  </template>
  <script>
  import {mixin} from '../mixins'
  import {mapGetters} from 'vuex'
  import AlbumContent from '../components/AlbumContent'
  import Comment from '../components/Comment'
  import {getMarkOf, setMark,getCollectOfUserId, setCollect} from '../api'
  import {addFoodNums} from '../api/index'
  export default {
    name: 'food-album',
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
        average: 0,         //展示出来的平均分
        mark:0,//打的分数
        typeS:0,//分数的类型，0美食1合集2窗口
        }
    },
    computed: {
            ...mapGetters([
            'listOfFoods',      //当前美食列表
            'tempList',         //当前窗口对象
            'loginIn',          //用户是否已登录
            'userId',           //当前登录用户id
            'isActive',         //当前的美食是否已收藏
        ])
    },
    created () {
        this.foodId = this.$route.params.sid;//当前页面路由中的sid值即当前美食id
        this.getMark(this.foodId)//获取当前美食分数
        this.showCollected(this.foodId)//展示被收藏的状态即小红心是否点亮
        addFoodNums(this.foodId)//用户点开当前美食页面就会增加一次该美食被点击次数
    },
  
    methods: {
      //获取美食评分
      getMark () {
        getMarkOf(this.typeS,this.foodId)
          .then(res => {
            this.average = res / 2
          })
          .catch(err => {
            console.log(err)
          })
      },
      //提交评分
      setMark(){
        if (this.loginIn) {
          let params = new URLSearchParams();
          params.append('consumerId', this.userId)
          params.append('typeS',0);//评分类型，0美食
          params.append('foodId',this.foodId);//当前的美食id
          params.append('score', this.mark * 2)//用户打的分数
          setMark(params)//将params传给后端，如果后端传回来1表示评分成功
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
      },
      //收藏
      collection () {
        if (this.loginIn) {
          var params = new URLSearchParams()
          params.append('userId', this.userId)
          params.append('type', 0)//收藏类型，0美食
          params.append('foodId', this.foodId)//当前美食id
          setCollect(params)//将params传给后端
            .then(res => {
              if (res.code === 1) {//如果后端传回来1表示收藏成功，并改变收藏按钮状态
                this.$store.commit('setIsActive', true)
                this.notify('收藏成功', 'success')
              } else if (res.code === 2) {//如果后端传回来2表示已经收藏过了
                this.notify('已收藏', 'warning')
              } else {
                this.notify('收藏失败', 'error')
              }
            })
        } else {
          this.notify('请先登录', 'warning')
        }
      },
      //显示收藏的不同状态
      showCollected(foodId){
        this.$store.commit('setIsActive', false)//通过改变isActive值改变颜色，默认显示黑色，表示未收藏
        if (this.loginIn) {//如果登录
          getCollectOfUserId(this.userId)//看用户是否收藏了
            .then(res => {
              for (let item of res) {//如果用户查出用户收藏列表里有该美食则调整图标状态为红色，表示已收藏
                if (item.foodId == foodId) {
                  this.$store.commit('setIsActive', true)
                  break
                }
              }
            })
        }
      }   
    }  
  }
  </script>
  
  <style lang="scss" scoped>
  @import '../assets/css/food-list-album.scss';
  </style>
  