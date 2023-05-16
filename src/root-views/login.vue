 <template>
 <!-- dataV-BorderBox11-1 -->
 <div class="login">
    <!-- EL-from-1 -->
    <el-form ref="ruleFormRef" :model="ruleForm" status-icon :rules="rules" class="demo-ruleForm" size="large"
      label-position="top">
      <el-form-item  label="用户名：username" prop="username" size="large">
        <el-input v-model="ruleForm.username" name="username" type="txt" />
      </el-form-item>
      <el-form-item  label="密码：password" prop="password" size="large">
        <el-input v-model="ruleForm.password" name="current-password" type="password" />
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
        <el-button type="primary" @click="submitForm(ruleFormRef)">提交</el-button>
        <el-button @click="resetForm(ruleFormRef)">重置</el-button>
      </el-form-item>
    </el-form>
    <!-- EL-from-2 -->
  </div>
  <!-- dataV-BorderBox11-2 -->
</template>
<script setup lang="ts">
import type { FormInstance, FormRules } from 'element-plus'
import { onMounted, reactive, ref, inject } from 'vue'
import { isLogin,RootLoginCode,RootLoginMessage,RootLoginRest} from '../JavaScript/RootLogin'
import {getCaptchaImg,isCoid,CaptchaCode,CaptchaMessage,CaptchaRest} from '../JavaScript/Captcha';
import { useRoute, useRouter } from "vue-router"
import Switch from '../components/Switch.vue'
import { addForever, getForever } from '../utils/localStorage'
// 全局数据组
const state = reactive({
  uuId:'-1',
  CaptchaImg:'',
  loginTrue: '', 
})
// 刚开始就执行
onMounted(async() => {
 await getCoide()
  logingMesg()
});
// 给子组件的参数
const Sstate = reactive({
//   LA: '记住信息于本地',
//   LI: '',
//   CA:'将信息保存到个人账户',
  SI:'普通管理员',
  SA:'系统管理员'
})
// 接受到的子组件参数
const clickEven = (val: any) => {
  if(val.SV==true){
    ruleForm.role='1'
  }else{
    ruleForm.role='0'
  }
}

const ruleForm = reactive({
  username: '',
  password: '',
  ucode: '',
  role:'0'
})
const router = useRouter()
// 获取验证码
const getCoide = async() => {
  await getCaptchaImg(state.uuId)
  state.CaptchaImg=CaptchaRest.KaptchaImage
  state.uuId=CaptchaRest.uuId
}
const ruleFormRef = ref<FormInstance>()
// 验证码
const checkCoid =(rule: any, value: any, callback: any) => {
  if  (value === '') {
    return callback(new Error('请输入验证码'))
  } else {
 
    //验证码判断延时
    setTimeout(async() => {
      await isCoid(state.uuId, ruleForm.ucode)
      console.log("判断验证码", ruleForm.ucode, '值',CaptchaCode);
      if (CaptchaCode===1) {
        callback(new Error(CaptchaMessage))
      } else if (CaptchaCode===0) {
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
const rules =reactive<FormRules>({
  username: [{ validator: validateUsername, trigger: 'blur' }],
  password: [{ validator: validatePass, trigger: 'blur' }],
  ucode: [{ validator: checkCoid, trigger: 'blur' }],
})
//订单提交
const submitForm = async (formEl: FormInstance | undefined) => {
  if (!formEl) return
  await formEl.validate(async (valid) => {
    if (valid) {
      console.log("登录类型",ruleForm.role);
     await isLogin(ruleForm)//登录函数
      if ( RootLoginCode=== 0) {
        console.log("登录成功");
        await router.push("/root")
        location.reload()//刷新
      } else if (RootLoginCode=== 1) {
        alert(RootLoginMessage)
      } 
      alert(RootLoginMessage)
      console.log('submit!')
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
