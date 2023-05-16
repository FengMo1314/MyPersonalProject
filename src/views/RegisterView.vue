<template>
  <div class="register">
    <el-form ref="ruleFormRef" :model="ruleForm" status-icon :rules="rules" label-width="120px" class="demo-ruleForm" size="large" label-position="top">
      <el-form-item label="用户名" prop="username" size="large">
        <el-input v-model.number="ruleForm.username" />
      </el-form-item>
      <el-form-item label="密码" prop="password" size="large">
        <el-input v-model="ruleForm.password" type="password" autocomplete="off" />
      </el-form-item>
      <el-form-item label="密码确认" prop="checkPass" size="large">
        <el-input v-model="ruleForm.checkPass" type="password" autocomplete="off" />
      </el-form-item>
      <el-form-item label="验证码：" prop="ucode" size="large">
        <span style="margin-right: 40px;">看不清点击图片刷新</span>
        <el-image :src="state.CaptchaImg" @click="getCoide()" style="min-width:40%;" />
        <el-input v-model="ruleForm.ucode" />
      </el-form-item>
      <el-form-item size="large">
        <Switch />
      </el-form-item>
      <el-form-item size="large">
        <el-button type="primary" @click="submitForm(ruleFormRef)">注册</el-button>
        <el-button @click="resetForm(ruleFormRef)">重置</el-button>
      </el-form-item>
      <el-form-item size="large">
        <a href="/login">去登录</a>
      </el-form-item>
    </el-form>
  </div>
</template>
  
<script lang="ts" setup>
import { reactive, ref,onMounted } from 'vue'
import { useRoute, useRouter } from "vue-router"
import type { FormInstance, FormRules } from 'element-plus'
import { registers, registersUsernameExit, RegisterCode, RegisterMessage, RegisterRest } from '../JavaScript/Register'
import {getCaptchaImg,isCoid,CaptchaCode,CaptchaMessage,CaptchaRest} from '../JavaScript/Captcha';
import Switch from '../components/Switch.vue';
// 全局数据组
const state = reactive({
  uuId:'',
  CaptchaImg:'',
})
// 刚开始就执行
onMounted(() => {
  getCoide()
});
const getCoide=async()=>{ 
  await getCaptchaImg(state.uuId)
  state.CaptchaImg=CaptchaRest.KaptchaImage
  state.uuId=CaptchaRest.uuId
}
const router = useRouter()
const ruleFormRef = ref<FormInstance>()

const checkUsername = (rule: any, value: any, callback: any) => {
  if (!value) {
    return callback(new Error('请输入用户名：Please input the username'))
  }

  setTimeout(async () => {
    await registersUsernameExit(value)
    if (RegisterCode === 1) {
      return callback(new Error('用户名已经存在'))
    } else {
      callback()
    }
  }, 1000)
}

const validatePass = (rule: any, value: any, callback: any) => {
  if (value === '') {
    callback(new Error('请输入密码：Please input the password'))
  } else if (value.length < 6) {
    callback(new Error('密码最少六位数'))
  } else {
    if (ruleForm.checkPass !== '') {
      if (!ruleFormRef.value) return
      ruleFormRef.value.validateField('checkPass', () => null)
    }
    callback()
  }
}
const validatePass2 = (rule: any, value: any, callback: any) => {
  if (value === '') {
    callback(new Error('请再次输入密码：Please input the password again'))
  } else if (value !== ruleForm.password) {
    callback(new Error("两次密码不一致:Two inputs don't match!"))
  } else {
    callback()
  }
}

const ruleForm = reactive({
  password: '',
  checkPass: '',
  username: '',
  userType: 'user',
  ucode:''
})

const rules = reactive<FormRules>({
  password: [{ validator: validatePass, trigger: 'blur' }],
  checkPass: [{ validator: validatePass2, trigger: 'blur' }],
  username: [{ validator: checkUsername, trigger: 'blur' }],
})

const submitForm = async (formEl: FormInstance | undefined) => {
  if (!formEl) return
  await formEl.validate(async (valid) => {
    if (valid) {
      await registers(ruleForm)
      if (RegisterCode == 0) {
        alert("注册成功")
        router.push("/")
      }
      return true
    } else {
      console.log('error submit!')
      return false
    }
  })
}

const resetForm = (formEl: FormInstance | undefined) => {
  if (!formEl) return
  formEl.resetFields()
}
</script>
<style scoped>
.register {

  display: -moz-box;
  /*兼容Firefox*/
  display: -webkit-box;
  /*兼容FSafari、Chrome*/

  -moz-box-align: center;
  /*兼容Firefox*/
  -webkit-box-align: center;
  /*兼容FSafari、Chrome */

  -moz-box-pack: center;
  /*兼容Firefox*/
  -webkit-box-pack: center;
  /*兼容FSafari、Chrome */


}</style>
  