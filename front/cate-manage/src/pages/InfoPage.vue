<template>
  <div>
    <el-row :gutter="20" class="mgb20">
      <el-col :span="6">
        <el-card>
          <div class="grid-content">
            <div class="grid-cont-center">
              <div class="grid-num">{{ consumerCount }}</div>
              <div>用户总数</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card>
          <div class="grid-content">
            <div class="grid-cont-center">
              <div class="grid-num">{{ foodCount }}</div>
              <div>美食总数</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card>
          <div class="grid-content">
            <div class="grid-cont-center">
              <div class="grid-num">{{ foodWindowCount }}</div>
              <div>窗口数量</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card>
          <div class="grid-content">
            <div class="grid-cont-center">
              <div class="grid-num">{{ foodListCount }}</div>
              <div>美食合集数量</div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
    <el-row :gutter="20" class="mgb20">
      <el-col :span="12">
        <h3 class="mgb20">用户性别比例</h3>
        <div style="background-color:white">
          <ve-pie :data="consumerSex" :theme="options"></ve-pie>
        </div>
      </el-col>
      <el-col :span="12">
        <h3 class="mgb20">美食合集类型分布</h3>
        <div style="background-color:white">
          <ve-histogram :data="foodStyle"></ve-histogram>
        </div>
      </el-col>
    </el-row>
    <el-row :gutter="20" class="mgb20">
      <el-col :span="12">
        <h3 class="mgb20">窗口餐厅分布比例</h3>
        <div style="background-color:white">
          <ve-pie :data="foodWindowSex"></ve-pie>
        </div>
      </el-col>
      
    </el-row>
  </div>
</template>
<script>
import {
  getAllConsumer,
  allFood,
  getAllFoodWindow,
  getAllFoodList
} from '../api/index'

export default {
  data () {
    return {
      consumerCount: 0,       // 用户总数
      foodCount: 0,           // 美食总数
      foodWindowCount: 0,         // 窗口数量
      foodListCount: 0,        // 美食合集数量
      consumer: [],            // 所有用户
      consumerSex: {           // 按性别分类的用户数
        columns: ['性别', '总数'],
        rows: [
          {'性别': '男', '总数': 0},
          {'性别': '女', '总数': 0}
        ]
      },
      options: {
        color: ['#87cefa', '#ffc0cb']
      },
      options1: {
        color: ['yellow']
      },
      foodStyle: {           //按美食合集风格分类
        columns: ['类别', '总数'],
        rows: [
          {'类别': '饺子馄饨', '总数': 0},
          {'类别': '炸鸡汉堡', '总数': 0},
          {'类别': '特色小吃', '总数': 0},
          {'类别': '米饭快餐', '总数': 0},
          {'类别': '暖胃粉丝汤', '总数': 0},
          {'类别': '麻辣烫冒菜', '总数': 0},
        ]
      },
      foodWindowSex: {           //按性别分类的窗口数
        columns: ['窗口餐厅', '总数'],
        rows: [
          {'窗口餐厅': '东餐厅', '总数': 0},
          {'窗口餐厅': '西餐厅', '总数': 0},
        ]
      },
      
    }
  },
  created () {

  },
  mounted () {
    this.getConsumer()
    this.getFood()
    this.getFoodWindow()
    this.getFoodList()
  },
  methods: {
    getConsumer () {                     //用户总数
      getAllConsumer().then(res => {
        this.consumer = res
        this.consumerCount = res.length
        this.consumerSex.rows[0]['总数'] = this.setSex(1, this.consumer)
        this.consumerSex.rows[1]['总数'] = this.setSex(0, this.consumer)
      })
    },
    setSex (sex, val) {              //根据性别获取用户数
      let count = 0
      for (let item of val) {
        if (sex == item.sex) {
          count++
        }
      }
      return count
    },
    getFood () {                      //美食总数
      allFood().then(res => {
        this.foodCount = res.length
      })
    },
    getFoodWindow () {                      //窗口数量
      getAllFoodWindow().then(res => {
        this.foodWindowCount = res.length
        this.foodWindowSex.rows[0]['总数'] = this.setSex(0, res)
        this.foodWindowSex.rows[1]['总数'] = this.setSex(1, res)
        
      })
    },

    getFoodList () {                    //美食合集数量
      getAllFoodList().then(res => {
        this.foodListCount = res.length
        for (let item of res) {
          this.getByStyle(item.style)
        }
      })
    },
    getByStyle (style) {              //根据美食合集风格获取数量
      for (let item of this.foodStyle.rows) {
        if (style.includes(item['类别'])) {
          item['总数']++
        }
      }
    },
    
  }
}

</script>

<style scoped>
.grid-content {
  display: flex;
  align-items: center;
  height: 50px;
}

.grid-cont-center {
  flex: 1;
  text-align: center;
  font-size: 14px;
  color: darkgray;
}

.grid-num {
  font-size: 30px;
  font-weight: bold;
}
</style>
