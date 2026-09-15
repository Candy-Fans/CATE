<template>
  <div class="table">
    <div class="crumbs">
      <i class="el-icon-tickets"></i>美食信息
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
              height="680px" :data="data"
              @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="40"></el-table-column>
      <el-table-column label="美食图片" width="110" align="center">
        <template slot-scope="scope">
          <div class="food-img">
            <img :src="getUrl(scope.row.pic)" style="width:100%"/>
          </div>
        </template>
      </el-table-column>
      <el-table-column prop="name" label="美食名称" width="120" align="center"></el-table-column>
      <el-table-column prop="introduction" label="简介" align="center"></el-table-column>
      <el-table-column prop="price" label="价格" align="center"> </el-table-column>
      
      <el-table-column label="资源更新" align="center" width="100">
        <template slot-scope="scope">
          <el-upload :action="uploadUrl(scope.row.id)"
                     :before-upload="beforeAvatorUpload"
                     :on-success="handleAvatorSuccess">
            <el-button size="mini">更新图片</el-button>
          </el-upload>
        </template>
      </el-table-column>
      <el-table-column label="评论" width="80" align="center">
        <template slot-scope="scope">
          <el-button size="mini" @click="getComment(data[scope.$index].id,0)">评论
          </el-button>
        </template>
      </el-table-column>

      <el-table-column label="操作" width="150" align="center">
        <template slot-scope="scope">
          <el-button size="mini" @click="handleEdit(scope.row)">编辑</el-button>
          <el-button size="mini" type="danger"
                     @click="handleDelete(scope.row.id)">删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <div class="pagination">
      <el-pagination
        background
        layout="total,prev,pager,next"
        :current-page="currentPage"
        :page-size="pageSize"
        :total="tableData.length"
        @current-change="handleCurrentChange"
      >
      </el-pagination>
    </div>

    <el-dialog title="添加美食" :visible.sync="centerDialogVisible" width="400px"
               center>
      <el-form :model="registerForm" ref="registerForm" label-width="80px"
               action="" id="tf">
        <el-form-item prop="name" label="美食名称" size="mini">
          <el-input v-model="registerForm.name"
                    placeholder="美食名称"></el-input>
        </el-form-item>
        <el-form-item prop="introduction" label="简介" size="mini">
          <el-input v-model="registerForm.introduction"
                    placeholder="简介"></el-input>
        </el-form-item>
        <el-form-item prop="price" label="价格" size="mini">
          <el-input v-model="registerForm.price"
          type="number"    placeholder="价格"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer">
                <el-button size="mini"
                           @click="centerDialogVisible = false">取消</el-button>
                <el-button size="mini" @click="addFood">确定</el-button>
            </span>
    </el-dialog>

    <el-dialog title="修改美食" :visible.sync="editVisible" width="400px" center>
      <el-form :model="form" ref="form" label-width="80px">
        <el-form-item prop="name" label="美食名称" size="mini">
          <el-input v-model="form.name" placeholder="美食名称"></el-input>
        </el-form-item>
        <el-form-item prop="introduction" label="简介" size="mini">
          <el-input v-model="form.introduction" placeholder="简介"></el-input>
        </el-form-item>
        <el-form-item prop="price" label="价格" size="mini">
          <el-input v-model="form.price" type="number" placeholder="价格"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer">
                <el-button size="mini"
                           @click="editVisible = false">取消</el-button>
                <el-button size="mini" @click="editSave">确定</el-button>
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
import {mapGetters} from 'vuex'
import '@/assets/js/iconfont.js'
import {foodOfFoodWindowId, setFood,updateFood, delFood} from '../api/index'

export default {
  mixins: [mixin],
  data () {
    return {
      foodWindowId: '',               //窗口id
      foodWindowName: '',             //窗口名
      centerDialogVisible: false, //添加弹窗是否显示
      editVisible: false,         //编辑弹窗是否显示
      delVisible: false,          //删除弹窗是否显示
      registerForm: {      //添加框
        name: '',
        foodWindowName: '',
        introduction: '',
        price: 0,
      },
      form: {      //编辑框
        id: '',
        name: '',
        introduction: '',
        price: 0,
      },
      tableData: [],
      tempData: [],
      select_word: '',
      pageSize: 5,    //分页每页大小
      currentPage: 1,  //当前页
      idx: -1,          //当前选择项
      multipleSelection: [],   //哪些项已经打勾
    }
  },
  computed: {
    ...mapGetters([
      'isPlay'
    ]),
    //计算当前搜索结果表里的数据
    data () {
      return this.tableData.slice((this.currentPage - 1) * this.pageSize, this.currentPage * this.pageSize)
    }
  },
  watch: {
    //搜索框里面的内容发生变化的时候，搜索结果table列表的内容跟着它的内容发生变化
    select_word: function () {
      if (this.select_word == '') {
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
    this.foodWindowId = this.$route.query.id
    this.foodWindowName = this.$route.query.name
    this.getData()
  },
  methods: {
    //获取当前页
    handleCurrentChange (val) {
      this.currentPage = val
    }, 
    //查询所有窗口
    getData () {
      this.tempData = []
      this.tableData = []
      foodOfFoodWindowId(this.foodWindowId).then(res => {
        this.tempData = res
        this.tableData = res
        this.currentPage = 1
      })
    },
    addFood () {
      this.$refs['registerForm'].validate(valid => {
        if (valid) {
          let params = new URLSearchParams()
          params.append('name',  this.foodWindowName + '-' + this.registerForm.name)
          params.append('foodWindowId', this.foodWindowId)
          params.append('introduction', this.registerForm.introduction)
          params.append('price', this.registerForm.price)
          params.append('pic', '/img/foodPic/tubiao.jpg')
          setFood(params)
            .then(res => {
              if (res.code == 1) {
                this.getData()
                this.notify('添加成功', 'success')
              } else {
                this.notify('添加失败', 'error')
              }
            })
            .catch(err => {
              console.log(err)
            })
          this.centerDialogVisible = false
        }
      })

    },

    // //添加窗口
    // addFood () {
    //   let _this = this
    //   var form = new FormData(document.getElementById('tf'))
    //   form.append('foodWindowId', this.foodWindowId)
    //   form.set('name', this.foodWindowName + '-' + form.get('name'))
      
    //   var req = new XMLHttpRequest()
    //   req.onreadystatechange = function () {
    //     //req.readyState == 4 获取到返回的完整数据
    //     //req.status == 200 和后台正常交互完成
    //     if (req.readyState == 4 && req.status == 200) {
    //       let res = JSON.parse(req.response)
    //       if (res.code) {
    //         _this.getData()
    //         _this.registerForm = {}
    //         _this.notify(res.msg, 'success')
    //       } else {
    //         _this.notify('保存失败', 'error')
    //       }
    //     }
    //   }
    //   req.open('post', `${_this.$store.state.HOST}/food/add`, false)
    //   req.send(form)
    //   _this.centerDialogVisible = false
    // },
    //弹出编辑页面
    handleEdit (row) {
      this.editVisible = true
      this.form = {
        id: row.id,
        name: row.name,
        introduction: row.introduction,
        price:row.price,
      }
    },
    //保存编辑页面修改的数据
    editSave () {
      let params = new URLSearchParams()
      params.append('id', this.form.id)
      params.append('name',this.foodWindowName + '-' + this.form.name)
      params.append('introduction', this.form.introduction)
      params.append('price', this.form.price)

      updateFood(params)
        .then(res => {
          if (res.code == 1) {
            this.getData()
            this.notify('修改成功', 'success')
          } else {
            this.notify('修改失败', 'error')
          }
        })
        .catch(err => {
          console.log(err)
        })
      this.editVisible = false
    },
    //更新图片
    uploadUrl (id) {
      return `${this.$store.state.HOST}/food/updateFoodPic?id=${id}`
    },
    //删除一名窗口
    deleteRow () {
      delFood(this.idx)
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
    },
    // //上传美食之前的校验
    // beforeFoodUpload (file) {
    //   var testMsg = file.name.substring(file.name.lastIndexOf('.') + 1)
    //   if (testMsg != 'mp3') {
    //     this.$message({
    //       message: '上传文件只能是mp3格式',
    //       type: 'error'
    //     })
    //     return false
    //   }
    //   return true
    // },
    
    //上传美食成功之后要做的工作
    handleFoodSuccess (res) {
      let _this = this
      if (res.code == 1) {
        _this.getData()
        _this.$notify({
          title: '上传成功',
          type: 'success'
        })
      } else {
        _this.$notify({
          title: '上传失败',
          type: 'error'
        })
      }
    },
    // //更新美食url
    // uploadFoodUrl (id) {
    //   return `${this.$store.state.HOST}/food/updateFoodUrl?id=${id}`
    // },
    
    
    //转向该窗口的评论列表
    getComment (id,type) {
      this.$router.push({path: '/comment', query: {id,type}})
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
