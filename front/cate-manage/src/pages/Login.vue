<template>
  <div class="login-wrap">
    <div class="ms-title">"吃什么" 后台管理登录</div>
    <div class="ms-login">
      <el-tabs class="login-tab" v-model="activeName" type="border-card"
               @tab-click="handleClick">
        <el-tab-pane label="帐号密码登录" name="account">
          <el-form :model="ruleForm" :rules="rules" ref="ruleForm"
                   class="login-form">
            <el-form-item prop="username">
              <el-input v-model="ruleForm.username"
                        placeholder="用户名"></el-input>
            </el-form-item>
            <el-form-item prop="password">
              <el-input type="password" v-model="ruleForm.password"
                        placeholder="密码"></el-input>
            </el-form-item>
            <div class="login-btn">
              <el-button type="primary" @click="submitForm">登录</el-button>
            </div>
          </el-form>
        </el-tab-pane></el-tabs>
    </div>
  </div>
</template>

<script>
import {mixin} from '../mixins/index'
import {getLoginStatus} from '../api/index'

export default {
  
  mixins: [mixin],
  data () {
    return {
      activeName: 'account',
      ruleForm: {
        username: '',
        password: ''
      },
      rules: {
        username: [
          {required: true, message: '请输入用户名', trigger: 'blur'}
        ],
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'}
        ]
      }
    }
  },
  methods: {
    
    //选择标签卡
    handleClick (tab, event) {
      this.activeName = tab.name
      if (tab.name === 'qrcode') {
        this.isCreateQRCodeDom = true
      } else {
        this.isCreateQRCodeDom = false
      }
    },
    submitForm () {
      let params = new URLSearchParams()
      params.append('name', this.ruleForm.username)
      params.append('password', this.ruleForm.password)
      getLoginStatus(params)
        .then((res) => {
          if (res.code === 200) {
            localStorage.setItem('userName', this.ruleForm.username)
            this.$router.push('/Info')
            this.notify('登录成功', 'success')
          } else {
            this.notify(res.msg, 'error')
          }
        })
    }
  }
}
</script>

<style scoped>
.login-wrap {
  position: relative;
  background: url("../assets/img/background.jpg");
  background-attachment: fixed;
  background-position: center;
  background-size: cover;
  width: 100%;
  height: 100%;
}

.ms-title {
  /*linear-gradient(135deg,#69ff97,#00e4ff)*/
  position: absolute;
  top: 50%;
  width: 100%;
  margin-top: -330px;
  text-align: center;
  font-size: 50px;
  font-weight: 600;
  -webkit-background-clip: text;
  color: transparent;
  background-image: linear-gradient(135deg, #ffffff, #d49726, #00f0d8, #9c0832);
  animation: title-sparkling 5s infinite linear alternate;
}

.ms-login {
  display: flex;
  justify-content: center;
  position: absolute;
  left: 53%;
  top: 50%;
  width: 300px;
  height: 350px;
  margin-left: -190px;
  margin-top: -150px;
  padding: 10px;
  border-radius: 5px;
  background: #fff;
  animation: sparkling 5s infinite linear alternate;
}

/*标题光晕闪烁动画*/
@keyframes title-sparkling {
  from {
    transform: scale(0.8);

  }
  to {
    transform: scale(1.6) translateY(20px);
  }
}

/*登录框光晕闪烁动画*/
@keyframes sparkling {
  /*linear-gradient(135deg,#9cecfb,#65c7f7,#0052d4)*/
  from {
    box-shadow: 1px 1px 50px 10px #65c7f7;
  }
  to {
    box-shadow: 1px 1px 100px 10px #dd97fb;
  }
}

.login-btn {
  height: 36px;
  line-height: 36px;
  text-align: center;

}

.login-btn button {
  width: 100%;
}

.login-tab {
  width: 100%;
  height: 100%;
}

.login-form {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  height: 230px;
}

.login-form:before {
  display: inline-block;
  content: "";
  height: 50px;
}

.login-form:after {
  display: inline-block;
  content: "";
  height: 100px;
}

.qrcode-box {
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  align-items: center;
}

.fresh-qrcode {
  color: #fff;
  margin: 20px 25px;
  padding: 15px;
  font-size: 16px
}

/*刷新二维码的中间状态*/
.fresh-qrcode-status {
  width: 200px;
  height: 200px;
}
</style>
