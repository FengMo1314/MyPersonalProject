<template>
    <div>
        <el-radio-group v-model="formSize" label="表单尺寸">
          <el-radio-button label="large">large</el-radio-button>
          <el-radio-button label="default">default</el-radio-button>
          <el-radio-button label="small">small</el-radio-button>
        </el-radio-group>
        <el-radio-group v-model="labelPosition" label="对齐方式">
          <el-radio-button label="left">Left</el-radio-button>
          <el-radio-button label="right">Right</el-radio-button>
          <el-radio-button label="top">Top</el-radio-button>
        </el-radio-group>
        <el-form-item label="编辑模式开关" prop="delivery">
            <el-switch v-model="ruleForm.delivery" />
        </el-form-item>
      </div>
    <el-form ref="ruleFormRef" 
    :model="ruleForm" 
    :rules="rules" 
    label-width="auto"
    class="demo-ruleForm" 
    :size="formSize"
    :label-position="labelPosition"
    :disabled="!ruleForm.delivery"
        status-icon>
        <el-form-item label="用户Id" prop="id">
            <el-input v-model="ruleForm.id" disabled />
        </el-form-item>
        <el-form-item label="用户名" prop="username" >
            <el-input v-model="ruleForm.username"/>
        </el-form-item>
        <el-form-item label="用户密码" prop="userpassword" >
            <el-input v-model="ruleForm.userpass" show-password/>
        </el-form-item>
        <el-form-item label="用户邮箱" prop="userEmail" >
            <el-input v-model="ruleForm.email" :placeholder="ruleForm.email">
                <template #prepend>
                    <el-button :icon="Message" />
                </template>
            </el-input>
        </el-form-item>
        <el-form-item label="用户电话" prop="userPhone">
            <el-input v-model="ruleForm.userPhone" :placeholder="ruleForm.userPhone">
                <template #prepend>
                    <el-button :icon="Phone" />
                </template>
            </el-input>
        </el-form-item>
        <el-form-item label="Activity count" prop="count">
            <el-select-v2 v-model="ruleForm.count" placeholder="Activity count" :options="options" />
        </el-form-item>
        <el-form-item label="用户生日信息" required>
            <el-col :span="11">
                <el-form-item prop="userBirthday">
                    <el-date-picker v-model="ruleForm.userBirthday" type="date" label="生日" placeholder="出生日期"
                        style="width: 100%" />
                </el-form-item>
            </el-col>
            <el-col class="text-center" :span="2">
                <span class="text-gray-500">-</span>
            </el-col>
            <el-col :span="11">
                <el-form-item prop="date2">
                    <el-time-picker v-model="ruleForm.date2" label="Pick a time" placeholder="Pick a time"
                        style="width: 100%" />
                </el-form-item>
            </el-col>
        </el-form-item>
        <!-- 多选类型 -->
        <!-- <el-form-item label="Activity type" prop="type">
        <el-checkbox-group v-model="ruleForm.type">
          <el-checkbox label="Online activities" name="type" />
          <el-checkbox label="Promotion activities" name="type" />
          <el-checkbox label="Offline activities" name="type" />
          <el-checkbox label="Simple brand exposure" name="type" />
        </el-checkbox-group>
      </el-form-item> -->
        <!-- 单选框类型 -->
        <el-form-item label="身份类型" prop="userType">
            <el-radio-group v-model="ruleForm.userType">
                <el-radio label="root" disabled/>
                <el-radio label="user" />
            </el-radio-group>
        </el-form-item>
        <el-form-item label="自我介绍" prop="desc">
            <el-input v-model="ruleForm.desc" type="textarea"/>
        </el-form-item>
        <el-form-item>
            <el-button type="primary" @click="submitForm(ruleFormRef)">
                提交
            </el-button>
            <el-button @click="resetForm(ruleFormRef)">重置</el-button>
        </el-form-item>
    </el-form>
</template>
  
<script lang="ts" setup>
import { reactive, ref, onMounted } from 'vue'
import type { FormInstance, FormRules } from 'element-plus'
import { Message,Phone } from '@element-plus/icons-vue'
import { getUById, updatedU, UsersCode, UsersMessage, UsersRest } from '../JavaScript/Users'
import { loginOutByuser } from '../JavaScript/LoginOut'
// 表单尺寸：large，default，small
const formSize = ref('large')
// 表单标题位置：top,left,right
const labelPosition = ref('top')
// 
const ruleFormRef = ref<FormInstance>()
const ruleForm = reactive({
    id: 0,
    username: '',
    userpass:'',
    email: '',
    userPhone:'',
    count: '',
    userBirthday: '',
    date2: '',
    delivery:false,
    type: [],
    userType: 'user',
    desc: '这个人很懒-没有填写',
})
onMounted(async () => {
   await setUserTb()
})
// 
const setUserTb=async()=>{
    await getUById()
    if(UsersCode!=0){
        loginOutByuser()
        return
    }
    ruleForm.email = UsersRest.email
    ruleForm.id = UsersRest.id
    ruleForm.userpass=UsersRest.password
    ruleForm.username = UsersRest.username
    ruleForm.userType = UsersRest.type
    ruleForm.userPhone=UsersRest.phone
}
// 验证规则
const rules = reactive<FormRules>({
    username: [
        { required: true, message: 'Please input Activity name', trigger: 'blur' },
    ],
    userpassword:[
        {
            type:'string',
            required:false,
            message:'请输入密码',
            trigger: 'blur',
        },{
            min:6,
            max:20,
            message:'密码长度限制6到20位数',
            trigger: 'change',
        },
    ],
    userEmail: [
        {   
            type:'email',
            // 必填选项
            required: false,
            message: '请检查电子邮箱',
            trigger: 'change',
        },
    ],
    userPhone:[
        {
            type:'string',
            required:false,
            message:'请确认电话',
            trigger: 'change',
        }
    ],
    count: [
        {
            required: true,
            message: 'Please select Activity count',
            trigger: 'change',
        },
    ],
    userBirthday: [
        {
            type:'date',
            required:false,
            message: '请检查生日',
            trigger: 'blur',
        },
    ],
    date2: [
        {
            type: 'date',
            required: false,
            message: '具体出生时间',
            trigger: 'blur',
        },
    ],
    // type: [
    //     {
    //         type: 'array',
    //         required: true,
    //         message: 'Please select at least one activity type',
    //         trigger: 'change',
    //     },
    // ],
    // 用户类型
    userType: [
        {
            required: true,
            message: '请选择用户类型',
            trigger: 'change',
        },
    ],
    // 文本域
    desc: [
        { required:false, message: 'Please input activity form', trigger: 'blur' },
    ],
})

const submitForm = async (formEl: FormInstance | undefined) => {
    if (!formEl) return
    await formEl.validate(async(valid, fields) => {
        if (valid) {
           await updatedU({
                id:ruleForm.id,
                username:ruleForm.username,
                password:ruleForm.userpass,
                email:ruleForm.email,
                phone:ruleForm.userPhone,
                type:ruleForm.userType,
            })
            if(UsersCode===0){
                alert("更新OK")
              
            }else if(UsersCode===1){
                alert("用户名已存在")
                await setUserTb()
            }
            console.log('submit!')
            ruleForm.delivery=false
        } else {
            alert("请完成表单验证")
            console.log('error submit!', fields)
        }
    })
}

const resetForm = (formEl: FormInstance | undefined) => {
    if (!formEl) return
    formEl.resetFields()
}

const options = Array.from({ length: 10000 }).map((_, idx) => ({
    value: `${idx + 1}`,
    label: `${idx + 1}`,
}))
</script>
<style>
.el-radio-group {
    margin-right: 12px;
  }
</style>
  