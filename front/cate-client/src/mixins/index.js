import {mapGetters} from 'vuex'
import {likeFoodOfName, addFoodNums,} from '../api/index'

export const mixin = {
  computed: {
    ...mapGetters([
      'loginIn',              //用户是否已登录
      'userId',               //当前登录用户的id
    ])
  },
  methods: {
    //提示信息
    notify (title, type) {
      this.$notify({
        title: title,
        type: type
      })
    },
    
    //获取图片地址
    attachImageUrl (srcUrl) {
      return srcUrl ? this.$store.state.configure.HOST + srcUrl : this.$store.state.configure.HOST + '/img/user.jpg'
    },
    //根据窗口名字模糊查询美食
    getFood () {
      if (!this.$route.query.keywords) {
        this.$store.commit('setListOfFoods', [])
        this.notify('您输入的内容为空', 'warning')
      } else {
        likeFoodOfName(this.$route.query.keywords).then(res => {
          if (!res.length) {
            this.$store.commit('setListOfFoods', [])
            this.notify('系统暂未收录符合条件的美食', 'warning')
          } else {
            this.$store.commit('setListOfFoods', res)
          }
        }).catch(err => {
          console.log(err)
        })
      }
    },
    //获取名字前半部分--窗口名
    replaceLName (str) {
      let arr = str.split('-')
      return arr[0]
    },
    //获取名字后半部分--美食名称
    replaceFName (str) {
      let arr = str.split('-')
      return arr[1]
    },
  }
}
