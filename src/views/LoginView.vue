<template>
  <!-- dataV-BorderBox11-1 -->
  <div class="login">
    <!-- EL-from-1 -->
    <el-form ref="ruleFormRef" :model="ruleForm" status-icon :rules="rules" class="demo-ruleForm" size="large"
      label-position="top">
      <el-form-item label="用户名：username" prop="username" size="large">
        <el-input v-model="ruleForm.username" name="username" type="txt" :autocomplete="state.autocomplete" />
      </el-form-item>
      <el-form-item label="密码：password" prop="password" size="large">
        <el-input v-model="ruleForm.password" name="current-password" type="password"
          :autocomplete="state.autocomplete" />
      </el-form-item>
      <el-form-item label="验证码：" prop="ucode" size="large">
        <span style="margin-right: 40px;">看不清点击图片刷新</span>
        <el-image :src="state.CaptchaImg" @click="getCoide()" style="min-width:40%;" />
        <el-input v-model="ruleForm.ucode" />
      </el-form-item>
      <el-form-item size="large" label="记住账号">
        <Switch :Pstate="Sstate" @clickChild="clickEven" />
      </el-form-item>
      <el-form-item size="large">
        <el-button 
        v-loading.fullscreen.lock="fullscreenLoading"
        type="primary" @click="submitForm(ruleFormRef)">登录</el-button>
        <el-button @click="resetForm(ruleFormRef)">重置</el-button>
      </el-form-item>
      <el-form-item size="large">
        <a href="/register">去注册</a>
      </el-form-item>
    </el-form>
    <!-- EL-from-2 -->
  </div>
  <!-- dataV-BorderBox11-2 -->
</template>
<script setup lang="ts">
import type { FormInstance, FormRules } from 'element-plus'
import { onMounted, reactive, ref, inject } from 'vue'
import { isLogin, isSYSLoginUser } from '../JavaScript/Login'
import { getCaptchaImg, isCoid, CaptchaCode, CaptchaMessage, CaptchaRest } from '../JavaScript/Captcha';
import { useRoute, useRouter } from "vue-router"
import Switch from '../components/Switch.vue'
import { addForever, getForever } from '../utils/localStorage'
import { ElLoading } from 'element-plus'
// 全局数据组
const state = reactive({
  // username: 'lh',
  // password: 'pass',
  // url: '',
  uuId: '1',
  CaptchaImg: '',
  // is: '',
  loginTrue: '',
  save: false,
  saveA: false,
  autocomplete: 'off',
  loginType: false
})
// 刚开始就执行
onMounted(async () => {
  await getCoide()
  await logingMesg()
  await saveMEST()
});
// 给子组件的参数
const Sstate = reactive({
  LA: '记住信息于本地',
  LI: '',
  CA: '将信息保存到个人账户',
  SI: '房源用户',
  SA: '万和用户'
})
// 接受到的子组件参数
const clickEven = (val: any) => {
  state.save = val.LV
  if (val.CV == true) {
    state.autocomplete = 'on'
  }
  state.saveA = val.CV
  state.loginType = val.SV
}

const ruleForm = reactive({
  username: '',
  password: '',
  ucode: ''
})
const router = useRouter()
// 获取验证码
const getCoide = async () => {
  await getCaptchaImg(state.uuId)
  console.log(CaptchaRest);
  state.CaptchaImg = CaptchaRest.KaptchaImage
  state.uuId = CaptchaRest.uuId
}
// 判断记住信息函数
const saveMEST = () => {
  if (state.save) {
    if (!window.localStorage) {
      alert("浏览器不支持localstorage");
      return false;
    } else {
      alert("安全提示：记住信息将把信息保存至您的浏览器;若想清除请自行操作")
      addForever('un', ruleForm.username)
      addForever('up', ruleForm.password)
    }
  }
  if (state.saveA) {
    state.autocomplete = 'on'
    console.log(state.autocomplete);
    alert("将信息保存至账户取决于浏览器的账户功能")
  }
}
const ruleFormRef = ref<FormInstance>()
// 验证码
const checkCoid = (rule: any, value: any, callback: any) => {
  if (value === '') {
    return callback(new Error('请输入验证码'))
  } else {
    //验证码判断延时
    setTimeout(async () => {
      await isCoid(state.uuId, ruleForm.ucode)
      console.log("判断验证码", ruleForm.ucode, '后端返回code', CaptchaCode);
      if (CaptchaCode === 1) {
        callback(new Error(CaptchaMessage))
      } else if (CaptchaCode === 0) {
        callback()
      } else {
        callback(new Error("系统错误"))
      }
    }, 1000)
  }
}
// 用户名
const validateUsername = (rule: any, value: any, callback: any) => {
  if (value === '') {
    callback(new Error('请输入用户名'))
  } else {
    if (ruleForm.password !== '') {
      if (!ruleFormRef.value) return
      ruleFormRef.value.validateField('checkPass', () => null)
    }
    callback()
  }
}
// 密码
const validatePass = (rule: any, value: any, callback: any) => {
  if (value === '') {
    callback(new Error('请输入密码'))
  }
  else {
    callback()
  }
}
// 读取本地储存登录信息
const logingMesg = () => {
  let username = getForever('un')
  let userpassword = getForever('up')
  if (username && userpassword) {
    ruleForm.username = username;
    ruleForm.password = userpassword
  }
}
const rules = reactive<FormRules>({
  username: [{ validator: validateUsername, trigger: 'blur' }],
  password: [{ validator: validatePass, trigger: 'blur' }],
  ucode: [{ validator: checkCoid, trigger: 'blur' }],
})
// 
const fullscreenLoading = ref(false)
const openFullScreen2 = async(is:boolean) => {
  fullscreenLoading.value = true
  if(is){
    fullscreenLoading.value = false
  }
  // setTimeout(() => {
  //   fullscreenLoading.value = false
  // }, 2000)
}
//订单提交
const submitForm = async (formEl: FormInstance | undefined) => {
  if (!formEl) return
  await formEl.validate(async (valid) => {
    if (valid) {

      console.log("登录类型", state.loginType);
      let code;
      saveMEST()
      if (state.loginType) {
        code = ((await isSYSLoginUser(ruleForm)).code)
      } else {
        code = (await isLogin(ruleForm)).code//登录函数
      }
      if (code === 0) {
        openFullScreen2(true)
        console.log("登录成功");
        await router.push("/")
        location.reload()//刷新
        return
      } else if (code === 1) {
        console.log("密码错误");
        alert("密码错误")
        return
      } else if (code === -1) {
        console.log("没有该用户名");
        router.push(('/regist'))
        return
      }

      console.log('submit!', code)
    } else {
      getCoide()//刷新验证码
      console.log('error submit!——前端判定不符合条件')
      return false
    }
  })
}
// 重置表单
const resetForm = (formEl: FormInstance | undefined) => {
  if (!formEl) return
  formEl.resetFields()
  getCoide()
}
</script>
<style scoped>
.login {
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

}
</style>
