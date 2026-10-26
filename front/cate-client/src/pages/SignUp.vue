<template>
<div>
   
    <div class="signUp">
        <div class="signUp-head">
            <span>用户注册</span>
        </div>
        <el-form :model="registerForm" ref="registerForm" label-width="70px" class="demo-ruleForm" :rules="rules">
            <el-form-item prop="username" label="用户名">
                <el-input v-model="registerForm.username" placeholder="用户名"></el-input>
            </el-form-item>
            <el-form-item prop="password" label="密码">
                <el-input type="password" v-model="registerForm.password" placeholder="密码"></el-input>
            </el-form-item>
            <el-form-item prop="sex" label="性别">
                <el-radio-group v-model="registerForm.sex">
                    <el-radio :label="0">女</el-radio>
                    <el-radio :label="1">男</el-radio>
                </el-radio-group>
            </el-form-item>
            <el-form-item prop="phoneNum" label="手机">
                <el-input v-model="registerForm.phoneNum" placeholder="手机"></el-input>
            </el-form-item>
            <el-form-item prop="email" label="邮箱">
                <el-input v-model="registerForm.email" placeholder="邮箱"></el-input>
            </el-form-item>
            <el-form-item prop="birth" label="生日">
                <el-date-picker v-model="registerForm.birth" placeholder="选择日期" style="width: 100%;"></el-date-picker>
            </el-form-item>
            <el-form-item prop="introduction" label="签名">
                <el-input v-model="registerForm.introduction" placeholder="签名"></el-input>
            </el-form-item>
            <el-form-item prop="location" label="地区">
                <el-select v-model="registerForm.location" placeholder="地区" style="width: 100%;">
                    <el-option v-for=" item in cities" :key="item.value" :label="item.label" :value="item.value"></el-option>
                </el-select>
            </el-form-item>
            <el-form-item prop="captchaCode" label="验证码">
                <div class="captcha-row">
                    <el-input v-model.trim="registerForm.captchaCode" placeholder="图片字符" :maxlength="5" autocomplete="off" :disabled="submitting"></el-input>
                    <button type="button" class="captcha-button" :disabled="captchaLoading || submitting" title="点击换一张" aria-label="刷新验证码" @click="refreshCaptcha">
                        <img v-if="captchaImage" :src="captchaImage" alt="注册验证码">
                        <span v-else>{{ captchaLoading ? '加载中' : '点击重试' }}</span>
                    </button>
                </div>
                <div class="captcha-hint">不区分大小写，5 分钟有效；点击图片可更换</div>
            </el-form-item>
            <div class="login-btn">
                <el-button @click="goback(-1)">取消</el-button>
                <el-button type="primary" :loading="submitting" :disabled="captchaLoading || !captchaId || registered" @click="submitRegister">确定</el-button>
            </div>
        </el-form>
    </div>
</div>
</template>
<script>

import {rules,cities} from '../assets/data/form'
import {mixin} from '../mixins'
import {SignUp, getRegistrationCaptcha} from '../api/index'

export default {
    name: 'sing-up',
    mixins: [mixin],
    components: {
    },
    data() {
        return {
            registerForm: {
                username: '',       //用户名
                password: '',       //密码
                sex: '',            //性别
                phoneNum: '',       //手机
                email: '',          //邮箱
                birth: '',          //生日
                introduction: '',   //签名
                location: '',       //地区
                captchaCode: ''
            },
            cities: [],            //所有的地区--省
            rules: {},              //表单提交的规则
            captchaId: '',
            captchaImage: '',
            captchaLoading: false,
            submitting: false,
            registered: false
        }
    },
    created() {
        this.rules = Object.assign({}, rules, {
            captchaCode: [
                {required: true, message: '请输入图片验证码', trigger: 'blur'},
                {pattern: /^[a-zA-Z0-9]{5}$/, message: '请输入 5 位图片字符', trigger: 'blur'}
            ]
        });
        this.cities = cities;
        this.refreshCaptcha();
    },
    methods:{
      async refreshCaptcha () {
          if (this.captchaLoading || this.submitting) return;
          this.captchaLoading = true;
          this.captchaId = '';
          this.captchaImage = '';
          this.registerForm.captchaCode = '';
          try {
              const res = await getRegistrationCaptcha();
              if (res.code !== 1 || !res.captchaId || !res.image) throw new Error('验证码加载失败');
              this.captchaId = res.captchaId;
              this.captchaImage = res.image;
          } catch (err) {
              this.notify('验证码加载失败，请点击重试', 'error');
          } finally {
              this.captchaLoading = false;
          }
      },
      //提交注册
      submitRegister(){
          if (this.submitting || this.captchaLoading || !this.captchaId || this.registered) return;
          this.$refs['registerForm'].validate((valid) => {
            if(valid && !this.submitting && !this.registered){
              let _this = this;
              let d = this.registerForm.birth;
              let datetime = d.getFullYear() + '-' +(d.getMonth() + 1) +'-' + d.getDate();
              let params = new URLSearchParams();
              params.append('username',this.registerForm.username);
              params.append('password',this.registerForm.password);
              params.append('sex',this.registerForm.sex);
              params.append('phoneNum',this.registerForm.phoneNum);
              params.append('email',this.registerForm.email);
              params.append('birth',datetime);
              params.append('introduction',this.registerForm.introduction);
              params.append('location',this.registerForm.location);
              params.append('avator','/img/user.jpg');
              params.append('captchaId',this.captchaId);
              params.append('captchaCode',this.registerForm.captchaCode);
              this.submitting = true;
              SignUp(params)
                .then(res => {
                  _this.submitting = false;
                  if(res.code == 1){
                    _this.registered = true;
                    _this.notify('注册成功','success');
                    setTimeout(function(){
                      _this.$router.push({path: '/'});
                    },2000);
                  }else{
                    _this.notify(res.msg || '注册失败','error');
                    _this.refreshCaptcha();
                  }
                })
                .catch(err => {
                  _this.submitting = false;
                  _this.notify('注册失败，请检查网络后重试','error');
                  _this.refreshCaptcha();
                })
            }
          });
        },
        goback(index){
            this.$router.go(index);
        }
    }
}
</script>

<style lang="scss" scoped>
@import '../assets/css/sign-up.scss';
.demo-ruleForm{
  padding: 10px;
}
.captcha-row {
  display: flex;
  align-items: center;
  gap: 8px;
}
.captcha-row .el-input {
  flex: 1;
  min-width: 0;
}
.captcha-button {
  flex: 0 0 130px;
  height: 40px;
  padding: 0;
  cursor: pointer;
  border: 1px solid #dcdfe6;
  border-radius: 4px;
  background: #fff;
  overflow: hidden;
}
.captcha-button img {
  display: block;
  width: 100%;
  height: 100%;
}
.captcha-hint {
  font-size: 12px;
  line-height: 20px;
  color: #606266;
}
</style>
