<template>
    <el-dialog v-model="userState.dialogVisible" title="编辑信息状态" width="40%" draggable>
        <!-- 添加用户。el表单 ——-:rules=rules:添加验证规则 -->
        <el-form :label-position="labelPosition" label-width="100px" :model="userState.userAdd" :rules="rules"
            style="max-width: 460px" ref="ruleFormRef">
            <el-form-item label="ID">
                <el-input v-model="userState.userAdd.id" disabled />
            </el-form-item>
            <el-form-item label="username" prop="username">
                <el-input v-model="userState.userAdd.username" placeholder="请输入用户名" autocomplete="off" clearable />
            </el-form-item>
            <el-form-item label="password" prop="password">
                <el-input v-model="userState.userAdd.password" type="password" placeholder="请输入密码" autocomplete="off"
                    show-password clearable />
            </el-form-item>
            <el-form-item label="email" prop="email">
                <el-input v-model="userState.userAdd.email" type="email" placeholder="请输入电子邮件" clearable />
            </el-form-item>
            <el-form-item label="phone" prop="phone">
                <el-input v-model="userState.userAdd.phone" placeholder="请输入电话号码" clearable />
            </el-form-item>
            <el-form-item label="type" prop="type">
                <el-input v-model="userState.userAdd.type" placeholder="请输入用户身份" clearable />
            </el-form-item>
        </el-form>
        <!--  -->
        <template #footer>
            <span class="dialog-footer">
                <el-button @click="userState.dialogVisible = false">取消(Cancel)</el-button>
                <el-button type="primary" @click="submitForm(ruleFormRef);">
                    <h4>确认/添加</h4>
                    <h4>Confirm/Add</h4>
                </el-button>
                <el-button @click="resetForm(ruleFormRef)">
                    <h4>重置</h4>
                    <h4>Reset</h4>
                </el-button>
            </span>
        </template>
    </el-dialog>
    <!-- 添加弹窗-2 -->
</template>
<script lang="ts" setup>
import { onMounted, reactive, ref, h} from 'vue'
import type { FormInstance, FormRules, Action } from 'element-plus'
import { addU,getUByPage, deleteU, updatedU,UsersCode,UsersMessage,UsersRest, getUByName,allU } from '../JavaScript/Users'

// 子组件接受父组件传参
// 接收父组件传递过来的值
const dataOfUsers = defineProps({
    userState: {
        type: Object
    },
    addItem: {
        type: Object
    }
})
//定义给父组件传递过去的方法
const emit =defineEmits(['addItem'])
//
const onAddItem = function (isTrue: boolean) {
    //调用父组件传递过来的方法，传入参数修改父组件的值 
    
    emit('addItem', isTrue)
}

// 提交验证
const ruleFormRef = ref<FormInstance>()

// 添加表单的对齐方式
const labelPosition = ref('right')
// 标记添加是否成功,以便同步动态添加表格行
let isSucss = ref(false)
// ———————验证规则————————————————————————————————————————————————————————————————————————
// 具体验证方法
const validateUsername = (rule: any, value: any, callback: any) => {
    if (value === '') {
        callback(new Error('用户名不能为空'))
    } else {
        callback()
    }
}
const validatePassword = (rule: any, value: any, callback: any) => {
    if (value === '') {
        callback(new Error('密码不能为空'))
    } else if (value.length < 6) {
        if (dataOfUsers.userState.userAdd.id != 0) {//更新用户
            callback()
           return
        }
        callback(new Error("密码最少6位"))
    } else {
        callback()
    }
}
const validateUserType = (rule: any, value: any, callback: any) => {
    if (value === '') {
        callback(new Error('用户类型不能为空'))
    }else if(value==='user'|| value==='root'){
        callback()
    } else {
        callback(new Error('用户类型非法'))
    }
}
// 表单验证规则
const rules = reactive<FormRules>({
    username: [{ validator: validateUsername, trigger: 'blur' }],
    password: [{ validator: validatePassword, trigger: 'blur' }],
    type: [{ validator: validateUserType, trigger: 'blur' }],
})
// 提交验证
const submitForm =(formEl: FormInstance | undefined) => {
    if (!formEl) return
    formEl.validate(async(valid) => {
        if (valid) {
            console.log("dataOfUsers.userState.userAdd.id",dataOfUsers.userState.userAdd.id);
            if (dataOfUsers.userState.userAdd.id === 0) {//id=0,为添加用户
               await addU(dataOfUsers.userState.userAdd)
                if (UsersCode === 0) {//返回为成功
                    // 标识添加成功
                    isSucss.value = true
                    console.log("标识", isSucss.value);
                    // 初始话父组件的公共共享userAdd区域
                    // dataOfUsers.userState.userAdd.id = 0, dataOfUsers.userState.userAdd.username = '', dataOfUsers.userState.userAdd.password = '', dataOfUsers.userState.userAdd.email = '', dataOfUsers.userState.userAdd.phone = ''
                    console.log('submit!')
                    alert("添加/跟新成功！请继续")
                    // 调用动态添加行
                    onAddItem(isSucss.value)
                } else if (UsersCode=== 1) {
                    alert("用户名已存在")
                    console.log("用户名已存在");
                } else {//code=-1为添加失败
                    alert("添加失败")
                    console.log("添加失败");
                }
            } else {//id!=0——更新
                // 进行更新
               await updatedU(dataOfUsers.userState.userAdd)
                if (UsersCode=== 0) {
                   await getUByPage(dataOfUsers.userState.currentPage,
                   dataOfUsers.userState.pageSize)
                   dataOfUsers.userState.tableData=UsersRest.list
                    console.log('submit!')
                    // 初始话父组件的公共共享userAdd区域
                    dataOfUsers.userState.userAdd.id = 0, dataOfUsers.userState.userAdd.username = '', dataOfUsers.userState.userAdd.password = '', dataOfUsers.userState.userAdd.email = '', dataOfUsers.userState.userAdd.phone = '',dataOfUsers.userState.userAdd.type='user'
                    alert("更新成功！请继续")
                   
                    dataOfUsers.userState.dialogVisible=false;
                } else {
                    alert("更新失败")
                }
            }
        } else {
            console.log('error submit!')
            alert("请完成表单验证信息")
        }
    })

}


// 重置表单
const resetForm = (formEl: FormInstance | undefined) => {
    if (!formEl) return
    formEl.resetFields()
    // dataOfUsers.userState.userAdd.username = '', dataOfUsers.userState.userAdd.password = '', dataOfUsers.userState.userAdd.email = '', dataOfUsers.userState.userAdd.phone = ''
}

// ——————————————————————————————————————————————————————————————————————————————————————
</script>
