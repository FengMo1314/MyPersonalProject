<template>
  <div class="box">
        <el-header style="text-align: center;">
          <h2>商品类型管理</h2>
        </el-header>
      <!-- <el-scrollbar> -->
        <el-header>
          <el-button @click="dialogVisible = true">
            添加商品类型
          </el-button>
        </el-header>
        <!-- <el-main> -->
          <el-table :data="state.ShopType" style="width:100%;" border v-loading="loading">
            <el-table-column fixed prop="id" label="编号" align="center"/>
            <el-table-column prop="name" label="类型名称" align="center"/>
            <el-table-column prop="status" label="状态" align="center">
              <template #default="scope">
                <el-tag :type="scope.row.status === 0 ? 'success' : 'danger'" disable-transitions>{{
                  scope.row.status === 0 ? '启用' : '禁用' }}</el-tag>
              </template>
            </el-table-column>
            <el-table-column fixed="right" label="操作" align="center">
              <template #default="scope">
                <el-button plain type="primary" size="large" @click="handleClick(scope.row)">修改</el-button>
                <el-button plain :type="scope.row.status == '0' ?'danger':'success'" size="large" @click="chooseStatus(scope.row)">{{ scope.row.status == '0' ?
                  '禁用'
                  : '启用' }}</el-button>
              </template>
            </el-table-column>
          </el-table>
        <!-- </el-main> -->
      <!-- </el-scrollbar> -->
  </div>
  <el-dialog v-model="dialogVisible" title="添加商品类型" width="40%" draggable>
    <h3>类型名称</h3>
    <el-input v-model="input" placeholder="请输入商品类型名称" />
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="chickType()">
          添加
        </el-button>
      </span>
    </template>
  </el-dialog>
  <el-dialog v-model="dialogVisible2" title="修改商品类型" width="40%" draggable>
    <el-form ref="ruleFormRef" :model="ruleForm" status-icon :rules="rules" label-width="140px" class="demo-ruleForm" style="max-width:460px" label-position="top">
      <el-form-item label="编号" prop="id" >
        <el-input v-model="ruleForm.id" disabled />
      </el-form-item>
      <el-form-item label="类型名称" prop="name">
        <el-input v-model="ruleForm.name" />
        <h4 style="color: red;">{{errorMesg}}</h4>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-input v-model.number="ruleForm.status" disabled />
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible2 = false">取消</el-button>
        <el-button type="primary" @click="submitForm(ruleFormRef)">
          修改
        </el-button>
        <el-button @click="resetForm(ruleFormRef)">重置</el-button>
      </span>
    </template>
  </el-dialog>
</template>
  
<script lang="ts" setup>
import { getAll, addType, updateType, getByName, ShopTypeCode, ShopTypeMessage, ShopTypeRest } from '../../JavaScript/ShopType'
import { reactive, ref, onMounted } from 'vue'
import type { FormInstance, FormRules } from 'element-plus'
// 定义类型
export interface ShopType {
    id: number
    name: string
    status:number
}
// 全局数据组
const state = reactive({
  ShopType:<ShopType[]> [],
  addTypes: [],
})
// 注册一个回调函数，在组件挂载完成后执行。
onMounted(
  () => {
    all()
  })
const dialogVisible = ref(false)
//
const input = ref('')
const dialogVisible2 = ref(false)
const errorMesg=ref('')
const chickType = async () => {
  await addType({ 'name': input.value })
  alert(ShopTypeMessage)
  all()
  dialogVisible.value = false
}
// 
const loading = ref(true)
// 获取后台全部库存
const all = async() => {
  loading.value=true
  await getAll()
  state.ShopType = ShopTypeRest
  if(state.ShopType.length>0){
      loading.value=false
  }
}
// 编辑
const handleClick = (Type:ShopType) => {
  console.log("state.addTypes", state.addTypes)
  ruleForm.id =Type.id
  ruleForm.name = Type.name
  ruleForm.status =Type.status
  dialogVisible2.value = true;
}
const ruleFormRef = ref<FormInstance>()
const checkName = (rule: any, value: any, callback: any) => {
  if (!value) {
    return callback(new Error('请输入类型名称'))
  }
  getByName(value)
  setTimeout(() => {
    if (ShopTypeCode ===1) {
      callback()
    } else {
      callback(new Error("类型名重复"))
    }
    // if (!Number.isInteger(value)) {
    //   callback(new Error('Please input digits'))
    // } else {
    //   if (value < 18) {
    //     callback(new Error('Age must be greater than 18'))
    //   } else {
    //     callback()
    //   }
    // }
  }, 1000)
}
// 表单模板
const ruleForm = reactive({
  id:-1,
  name: '',
  status:-1,
})
// 验证规则
const rules = reactive<FormRules>({
  // name: [{ validator: checkName, trigger: 'blur' }],
})
// 提交
const submitForm = async (formEl: FormInstance | undefined) => {
  if (!formEl) return
  formEl.validate(async (valid) => {
    if (valid) {
      await updateType({ id: ruleForm.id, name: ruleForm.name, status: 0 })
      if (ShopTypeCode === 0) {
        alert(ShopTypeMessage)
        state.addTypes = [],
          dialogVisible2.value = false;
      }else{
        errorMesg.value=ShopTypeMessage
      }
      console.log('submit!')
      all()
    } else {
      console.log('error submit!')
      return false
    }
  })
}
// 重置
const resetForm = (formEl: FormInstance | undefined) => {
  if (!formEl) return
  formEl.resetFields()
}
// 禁用-启用:状态事件
const chooseStatus = async (mesg) => {
  let status = mesg.status
  if (status === 0) {
    status = 1
  } else {
    status = 0
  }
  await updateType({ id: mesg.id, name: mesg.name, status: status })
  all()
}
</script>
<style scoped>
body {
  margin: 0;
}
.example-showcase .el-loading-mask {
  z-index: 9;
}
</style>
  