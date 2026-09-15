<template><!-- 具体美食合集的详情页面 -->
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
          <h3>合集评分：</h3>
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
          <template slot="title">美食合集</template>
        </album-content>
        <comment :playId="foodListId" :type="1"></comment>
      </div>
    </div>
  </div>
</template>
<script>
import {mixin} from '../mixins'
import {mapGetters} from 'vuex'
import {listFoodDetail, foodOfFoodId, setMark, getMarkOf} from '../api/index'
import AlbumContent from '../components/AlbumContent'
import Comment from '../components/Comment'

export default {
  name: 'food-list-album',
  mixins: [mixin],
  components: {
    AlbumContent,
    Comment
  },
  data () {
    return {
      foodLists: [],      //当前页面需要展示的美食列表
      foodListId: '',    //前面传来的美食合集id
      average: 0,         //平均分
      mark: 0,         //提交评价的分数
      typeS:1,
    }
  },
  computed: {
    ...mapGetters([//存到缓存里的前面页面的内容
      'listOfFoods',      //当前美食列表
      'tempList',         //当前美食合集对象
      'loginIn',          //用户是否已登录
      'userId',           //当前登录用户id
    ])
  },
  created () {
    this.foodListId = this.$route.params.id//当前页面路由中的id值即当前美食合集id
    this.getFoodId()
    this.getMark()//获取评分
  },
  methods: {
    //获取当前美食合集的美食列表
    getFoodId () {
      listFoodDetail(this.foodListId)
        .then(res => {
          for (let item of res) {
            this.getFoodList(item.foodId)
          }
          this.$store.commit('setListOfFoods', this.foodLists)
        })
        .catch(err => {
          console.log(err)
        })
    },
    //根据美食id获取美食信息
    getFoodList (id) {
      foodOfFoodId(id)
        .then(res => {
          console.log("=======>>美食列表",res)
          this.foodLists.push(res)
        })
        .catch(err => {
          console.log(err)
        })
    },
    //获取美食合集评分
    getMark () {
        getMarkOf(this.typeS,this.foodListId)
          .then(res => {
            this.average = res / 2
          })
          .catch(err => {
            console.log(err)
          })
    },
    setMark(){
        if (this.loginIn) {
          let params = new URLSearchParams();
          params.append('consumerId', this.userId)
          params.append('typeS',1);
          params.append('foodListId',this.foodListId);
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
      },
  }
}
</script>

<style lang="scss" scoped>
@import '../assets/css/food-list-album.scss';
</style>
