<template>  
    <div class="eatWhat">  
        <el-button type="text" @click="dialogVisible = true">吃什么 Dialog</el-button>
        <el-dialog
            title="提示"
            :visible.sync="dialogVisible"
            width="30%"
            :before-close="handleClose">
        <span>这是一段信息</span>
        <span slot="footer" class="dialog-footer">
            <el-button @click="dialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
        </span>
        </el-dialog>
    </div>  
</template>  
    
<script>  
  export default {  
    data() {
      return {
        dialogVisible: false
      };
    },  
    methods: {  
        handleClose(done) {
            this.$confirm('确认关闭？')
            .then(_ => {
                done();
            })
            .catch(_ => {});
        },
        getRandomFood() {  
            // 发送请求到后端获取随机食物  
            this.$axios.get('/api/food/random')  
            .then(response => {  
                this.selectedFood = response.data;  
            })  
            .catch(error => {  
                console.error('Error fetching random food:', error);  
            });  
        },  
    },  
  };  
</script>  

<style lang="scss" scoped>
@import '../assets/css/scroll-top.scss';
</style>