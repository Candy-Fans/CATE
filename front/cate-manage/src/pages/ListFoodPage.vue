<template>
  <div class="table">
    <div class="crumbs">
      <i class="el-icon-tickets"></i>美食合集美食信息
    </div>
    <div class="container">
      <div class="handle-box">
        <el-button type="primary" size="mini" @click="delAll">批量删除</el-button>
        <el-input v-model="select_word" size="mini" placeholder="请输入美食名"
                  class="handle-input"></el-input>
        <el-button type="primary" size="mini"
                   @click="centerDialogVisible = true">添加美食
        </el-button>
      </div>
    </div>
    <el-table size="mini" ref="multipleTable" border style="width:100%"
              height="680px" :data="tableData"
              @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="40"></el-table-column>
      <el-table-column prop="name" label="窗口-美食名"
                       align="center"></el-table-column>
      <el-table-column label="操作" align="center">
        <template slot-scope="scope">
          <el-button size="mini" type="danger" style="margin-right: 20px"
                     @click="handleDelete(scope.row.id)">删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog title="添加美食" :visible.sync="centerDialogVisible" width="400px"
               center>
      <el-form :model="registerForm" ref="registerForm" label-width="80px"
               action="" id="tf">
        <el-form-item prop="foodWindowName" label="窗口名字" size="mini">
          <el-input v-model="registerForm.foodWindowName"
                    placeholder="窗口名字"></el-input>
        </el-form-item>
        <el-form-item prop="foodName" label="美食名字" size="mini">
          <el-input v-model="registerForm.foodName"
                    placeholder="美食名字"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer">
                <el-button size="mini"
                           @click="centerDialogVisible = false">取消</el-button>
                <el-button size="mini" @click="getFoodId">确定</el-button>
            </span>
    </el-dialog>
    <el-dialog title="删除美食" :visible.sync="delVisible" width="300px" center>
      <div align="center">删除不可恢复，是否确定删除？</div>
      <span slot="footer">
                <el-button size="mini"
                           @click="delVisible = false">取消</el-button>
                <el-button size="mini" @click="deleteRow">确定</el-button>
            </span>
    </el-dialog>
  </div>
</template>

<script>
import {mixin} from '../mixins/index'
import {
  listFoodDetail,
  foodOfFoodId,
  foodOfFoodName,
  listFoodAdd,
  delListFood
} from '../api/index'

export default {
  mixins: [mixin],
  data () {
    return {
      centerDialogVisible: false, //添加弹窗是否显示
      delVisible: false,          //删除弹窗是否显示
      registerForm: {      //添加框
        foodWindowName: '',     //窗口名字
        foodName: ''        //美食名字
      },
      tableData: [],
      tempData: [],
      select_word: '',
      idx: -1,          //当前选择项
      multipleSelection: [],   //哪些项已经打勾
      foodListId: ''          //美食合集id
    }
  },
  watch: {
    //搜索框里面的内容发生变化的时候，搜索结果table列表的内容跟着它的内容发生变化
    select_word: function () {
      if (this.select_word === '') {
        this.tableData = this.tempData
      } else {
        this.tableData = []
        for (let item of this.tempData) {
          if (item.name.includes(this.select_word)) {
            this.tableData.push(item)
          }
        }
      }
    }
  },
  created () {
    this.foodListId = this.$route.query.id
    this.getData()
  },
  methods: {
    //查询所有窗口
    getData () {
      this.tempData = []
      this.tableData = []
      listFoodDetail(this.foodListId).then(res => {
        for (let item of res) {
          this.getFood(item.foodId)
        }
      })
    },
    //根据美食id查询美食对象，放到tempData和tableData里面
    getFood (id) {
      foodOfFoodId(id)
        .then(res => {
          this.tempData.push(res)
          this.tableData.push(res)
        })
        .catch(err => {
          console.log(err)
        })
    },
    //添加美食前的准备，获取到美食id
    getFoodId () {
      let _this = this
      var foodOfName = _this.registerForm.foodWindowName + '-' + _this.registerForm.foodName
      foodOfFoodName(foodOfName).then(
        res => {
          _this.addFood(res[0].id)
        }
      )
    },
    //添加美食
    addFood (foodId) {
      let _this = this
      let params = new URLSearchParams()
      params.append('foodId', foodId)
      params.append('foodListId', this.foodListId)

      listFoodAdd(params)
        .then(res => {
          if (res.code === 1) {
            this.getData()
            this.notify('添加成功', 'success')
          } else {
            this.notify('添加失败', 'error')
          }
        })
        .catch(err => {
          console.log(err)
        })
      _this.centerDialogVisible = false
    },
    //删除一个美食
    deleteRow () {
      delListFood(this.idx, this.foodListId)
        .then(res => {
          if (res) {
            this.getData()
            this.notify('删除成功', 'success')
          } else {
            this.notify('删除失败', 'error')
          }
        })
        .catch(err => {
          console.log(err)
        })
      this.delVisible = false
    }
  }
}
</script>

<style scoped>
.handle-box {
  margin-bottom: 20px;
}

.food-img {
  width: 100%;
  height: 80px;
  border-radius: 5px;
  margin-bottom: 5px;
  overflow: hidden;
}

.handle-input {
  width: 300px;
  display: inline-block;
}

.pagination {
  display: flex;
  justify-content: center;
}

.play {
  position: absolute;
  z-index: 100;
  width: 80px;
  height: 80px;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  top: 18px;
  left: 15px;
}

.icon {
  width: 2em;
  height: 2em;
  color: white;
  fill: currentColor;
  overflow: hidden;
}

</style>
