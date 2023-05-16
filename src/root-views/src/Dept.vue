<template>
    <div class="box">
        <el-scrollbar>
            <el-header style="text-align: center;">
                <h2>部门管理</h2>
            </el-header>
            <el-header>
                <el-button @click="dialogVisibleAdd = true">
                    添加部门
                </el-button>
                <h3>{{ error }}</h3>
            </el-header>

            <el-table :data="state.deptDate" style="width:100%;" height="400" v-loading="loading" :element-loading-svg="svg"
                :element-loading-text="loadingMesg">
                <el-table-column fixed prop="dept_id" label="序号" />
                <el-table-column prop="dept_no" label="部门编号" />
                <el-table-column prop="dept_name" label="部门名称" />
                <el-table-column prop="remark" label="部门职能" />
                <el-table-column prop="fatherDept.dept_name" label="所属部门">
                    <template #default="scope1">
                        {{ scope1.row.fatherDept.dept_name === null ? '--' : scope1.row.fatherDept.dept_name }}
                    </template>
                </el-table-column>
                <el-table-column prop="is_valid" label="部门状态状态">
                    <template #default="scope">
                        <el-tag :type="scope.row.is_valid === 0 ? 'success' : 'danger'" disable-transitions>{{
                            scope.row.is_valid == 0 ? '有效' : '无效' }}</el-tag>
                    </template>
                </el-table-column>
                <el-table-column fixed="right" label="操作">
                    <template #default="scope">
                        <el-button link type="primary" size="small" @click="addSoneDeptShow(scope.row)">添加子部门</el-button>
                        <el-button link type="primary" size="small" @click="insterShow(scope.row)">修改</el-button>
                        <el-button link type="primary" size="small" @click="chooseStatus(scope.row)">{{ scope.row.is_valid
                            ==
                            '0' ? '禁用'
                            : '启用' }}</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </el-scrollbar>
    </div>
    <!-- 添加部门-1 -->
    <el-dialog v-model="dialogVisibleAdd" title="添加部门" width="40%" draggable>
        <el-form ref="ruleFormRef" :model="state.dept" status-icon :rules="rules" label-width="120px" class="demo-ruleForm">
            <el-form-item label="部门名称" prop="dept_name">
                <el-input v-model="state.dept.dept_name" />
            </el-form-item>
            <el-form-item label="部门职能" prop="remark">
                <el-input v-model="state.dept.remark" />
            </el-form-item>
        </el-form>
        <template #footer>
            <span class="dialog-footer">
                <el-button @click="dialogVisibleAdd = false">取消</el-button>
                <el-button type="primary" @click="submitAddDept()">
                    添加
                </el-button>
            </span>
        </template>
    </el-dialog>
    <!-- 修改部门 -->
    <el-dialog v-model="dialogVisibleInster" title="修改部门" width="40%" draggable>
        <h3>{{ error }}</h3>
        <el-form ref="ruleFormRef" :model="ruleForm" status-icon :rules="rules" label-width="120px" class="demo-ruleForm">
            <el-form-item label="id" prop="dept_id">
                <el-input v-model="ruleForm.dept_id" disabled />
            </el-form-item>
            <el-form-item label="部门名称" prop="dept_name">
                <el-input v-model="ruleForm.dept_name" />
            </el-form-item>
            <el-form-item label="部门职能" prop="remark">
                <el-input v-model.number="ruleForm.remark" />
            </el-form-item>
        </el-form>
        <template #footer>
            <span class="dialog-footer">
                <el-button @click="dialogVisibleInster = false">取消</el-button>
                <el-button type="primary" @click="submitInster(ruleFormRef)">
                    修改
                </el-button>
                <el-button @click="resetForm(ruleFormRef)">重置</el-button>
            </span>
        </template>
    </el-dialog>
    <!-- 添加子部门 -->
    <el-dialog v-model="dialogVisibleAddSone" title="添加子部门" width="40%" draggable>
        <h3>{{ error }}</h3>
        <el-form ref="ruleFormRef" :model="ruleForm" status-icon :rules="rules" label-width="120px" class="demo-ruleForm">
            <el-form-item label="父部门名称" prop="fatherDeptName">
                <el-input v-model="ruleForm.fatherDeptName" disabled />
            </el-form-item>
            <el-form-item label="部门名称" prop="dept_name">
                <el-input v-model="ruleForm.dept_name" />
            </el-form-item>
            <el-form-item label="部门职能" prop="remark">
                <el-input v-model.number="ruleForm.remark" />
            </el-form-item>
        </el-form>
        <template #footer>
            <span class="dialog-footer">
                <el-button @click="dialogVisibleAddSone = false">取消</el-button>
                <el-button type="primary" @click="submitAddSon()">
                    添加子部门
                </el-button>
                <el-button @click="resetForm(ruleFormRef)">重置</el-button>
            </span>
        </template>
    </el-dialog>
</template>
    
<script lang="ts" setup>
import { allDept, addDeptIng, disableIng, enableIng, updateDept, DeptCode, DeptMessage, DeptRest } from '../../JavaScript/Dept'
import { reactive, ref, onMounted } from 'vue'
import type { FormInstance, FormMetaProps, FormRules } from 'element-plus'
// 全局数据组
const state = reactive({
    dept: <dept>{},
    deptDate: <dept[]>[],
})
// 注册一个回调函数，在组件挂载完成后执行。
onMounted(
    () => {
        all()
    })
// 定义类型
interface dept {
    dept_id: number
    dept_name: string
    dept_no: string
    father_dept_id: number
    remark: string
    is_valid: number
}
// 获取后台全部库存
const all = async () => {
    loading.value = true
    await allDept()
    state.deptDate = DeptRest
    console.log("加载Dept", state.deptDate);
    if (state.deptDate.length > 0) {
        loading.value = false
    }

}
// 部门添加框
const dialogVisibleAdd = ref(false)
// 修改部门
const dialogVisibleInster = ref(false)
// 添加子部门
const dialogVisibleAddSone = ref(false)
// 
const error = ref('')
// 加载
const loading = ref(true)
// 
const loadingMesg = ref('Loadinf...')
// 回显方法 
const show = async (Dept: dept, dis) => {
    ruleForm.dept_id = Dept.dept_id
    ruleForm.dept_name = Dept.dept_name
    ruleForm.dept_no = Dept.dept_no
    ruleForm.father_dept_id = Dept.dept_id
    ruleForm.is_valid = Dept.is_valid
    ruleForm.remark = Dept.remark
    ruleForm.fatherDeptName = Dept.dept_name
}
// 提交添加
const submitAddDept = async () => {
    await addDeptIng(state.dept)
    alert(DeptMessage)
    if (DeptCode == 0) {
        state.dept = <dept>{}
        dialogVisibleAdd.value = false
        all()
    }
}
// 添加子部门回显
const addSoneDeptShow = async (Dept: dept) => {
    await show(Dept, 'dept_name')
    dialogVisibleAddSone.value = true;
}

// 修改回显
const insterShow = async (Dept: dept) => {
    await show(Dept, 'remark')
    error.value=''
    dialogVisibleInster.value = true;
}
const ruleFormRef = ref<FormInstance>()
const checkName = (rule: any, value: any, callback: any) => {
    if (!value) {
        return callback(new Error('请输入类型名称'))
    }
    // getByName(value)
    setTimeout(() => {
        if (DeptCode === 1) {
            callback()
        } else {
            callback(new Error("类型名重复"))
        }
    }, 1000)
}
// 表单模板
const ruleForm = reactive({
    dept_id: -1,
    dept_name: '',
    dept_no: '',
    father_dept_id: -1,
    remark: '',
    is_valid: -1,
    fatherDeptName: ''
})
// 验证规则
const rules = reactive<FormRules>({
    // name: [{ validator: checkName, trigger: 'blur' }],
})
// 修改提交
const submitInster = async (formEl: FormInstance | undefined) => {
    if (!formEl) return
    formEl.validate(async (valid) => {
        if (valid) {
            console.log('ruleForm', JSON.stringify(ruleForm));
            await updateDept(<dept>{ dept_id: ruleForm.dept_id, dept_name: ruleForm.dept_name, remark: ruleForm.remark })
            if (DeptCode == 0) {
                alert(DeptMessage)
                dialogVisibleInster.value = false;
                state.dept = <dept>{}
                loadingMesg.value = '刷新ing...'
                await all()
            } else {
                error.value = DeptMessage
                console.log('submit!')
            }

        } else {
            console.log('error submit!')
            return false
        }
    })
}
// 添加子部门提交
const submitAddSon = async () => {
    await addDeptIng(ruleForm)
    if (DeptCode == 0) {
        dialogVisibleAddSone.value = false
        error.value = '添加子部门成功'
        loadingMesg.value = '刷新ing...'
        all()
    } else {
        error.value = DeptMessage
    }

}
// 重置
const resetForm = (formEl: FormInstance | undefined) => {
    if (!formEl) return
    formEl.resetFields()
}
// 禁用-启用:状态事件
const chooseStatus = async (mesg: dept) => {
    let status = mesg.is_valid
    switch (status) {
        case 0:
            await disableIng(mesg.dept_id)
            break;
        case 1:
            await enableIng(mesg.dept_id)
            break;
        default:
            error.value = DeptMessage
            break;
    }
    error.value = DeptMessage
    loadingMesg.value = '刷新ing...'
    await all()
}
const svg = `
        <path class="path" d="
          M 30 15
          L 28 17
          M 25.61 25.61
          A 15 15, 0, 0, 1, 15 30
          A 15 15, 0, 1, 1, 27.99 7.5
          L 15 15
        " style="stroke-width: 4px; fill: rgba(0, 0, 0, 0)"/>
      `
</script>
<style scoped>
body {
    margin: 0;
}

.example-showcase .el-loading-mask {
    z-index: 9;
}

h3 {
    color: brown;
}
</style>
    