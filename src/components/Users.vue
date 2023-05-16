<template>
  <div class="box">
  <el-container>
    <el-container class="head">
      <el-header>
        <!-- 多选按钮 -->
        <div style="margin:20px auto" v-if="state.tableData.length > 0">
          <el-button @click="deletUsersByIds(state.selectIds)">批量删除</el-button>
          <el-button @click="toggleSelection()">清除全选</el-button>
          <el-button @click="fishUId()">刷新用户ID</el-button>
        </div>
      </el-header>
      <el-footer>
        <!-- 分页条 -->
        <el-scrollbar>
          <el-pagination v-model:current-page="currentPage2" v-model:page-size="pageSize2" :page-sizes="state.pageSizes"
            :small="small" :disabled="disabled" :background="background" layout="total,sizes, prev, pager, next"
            :total="state.total" @size-change="handleSizeChange" @current-change="handleCurrentChange" />
          <hr class="my-4" />
        </el-scrollbar>
      </el-footer>
    </el-container>
    <el-main>
      <el-radio-group v-model="tableLayout">
        <el-radio-button label="fixed" />
        <el-radio-button label="auto" />
      </el-radio-group>
      <el-scrollbar>
        <!-- 表格展示-1 -->
        <!-- stripe:斑马纹，border：边框 ；:row-class-name="tableRowClassName"：多彩行-->
        <el-table :data="filterTableData" style="width:100%" ref="multipleTableRef"
          @selection-change="handleSelectionChange" max-height="600" :default-sort="{ prop: 'id', order: 'ascending' }"
          stripe border :row-class-name="tableRowClassName" v-if="state.tableData.length > 0">

          <el-table-column type="selection" />
          <el-table-column label="用户ID" prop="id" sortable align="center" :fixed="tableLayout=='fixed'?left:false"/>
          <el-table-column prop="username" label="用户名" align="center" column-key="username" />
          <el-table-column prop="password" label="用户密码" align="center" />
          <el-table-column prop="email" label="用户邮箱" align="center" :filters="state.filtersOfEmail"
            :filter-method="filterHandler" width="200" />
          <el-table-column prop="phone" label="用户电话" align="center" column-key="phone" />
          <el-table-column prop="type" label="用户身份" align="center" column-key="type" />
          <el-table-column label="动态操作" align="left" width="180" :fixed="tableLayout=='fixed'?right:false">
            <!-- 自定义表头结构 -->
            <template #header>
              <el-input v-model="search" size="small" placeholder="用户名搜索" />
            </template>
            <template #default="scope">
              <el-button link type="primary" size="large"
                @click.prevent="deleteRow(scope.$index, scope.row.id); allUByPage()">
                <p>删除</p>
                <p>Remove</p>
              </el-button>
              <el-button link type="primary" size="large"
                @click="state.dialogVisible = true; getUpdatesUsers(scope.row.id)">
                <p> 编辑/更新</p>
                <p>Edit/Update</p>
              </el-button>
            </template>
          </el-table-column>
          <BackTop/>
        </el-table>

        <!-- 空状态的占位 -->
        <el-empty v-else v-loading="loading" element-loading-text="数据加载中——稍后..." :element-loading-spinner="svg"
          element-loading-svg-view-box="-10, -10, 50, 50" element-loading-background="rgba(122, 122, 122, 0.8)">
          <!-- <el-button type="primary">Button</el-button> -->
        </el-empty>
        <!-- <el-button class="mt-4" style="width: 100%" @click="state.dialogVisible = true">
      <p>添加</p>
      <p>Add</p>
    </el-button> -->
      </el-scrollbar>
    </el-main>
    <el-footer class="footer">
      <el-button class="mt-4" style="width: 100%" @click="state.dialogVisible = true">
        <p>添加</p>
        <p>Add</p>
      </el-button>
    </el-footer>
  </el-container>
  <Dialog :userState="state" @addItem="onAddItem"/>
</div>
</template>
  
<script lang="ts" setup>
import BackTop from './BackTop.vue';
import { onMounted, reactive, ref, computed } from 'vue'
import { getUByPage, deleteU, deleteUIds, fish, UsersCode, UsersMessage, UsersRest } from '../JavaScript/Users'
// 导入子弹窗组件
import Dialog from './Dialog.vue';
import type { ElTable, TableColumnCtx } from 'element-plus'
import { left, right } from '@popperjs/core';
// 定义类型
interface User {
  id: number
  username: string
  password: string
  email: string
  phone: string
  type: string
}
const state = reactive({
  tableData: [
  ],
  userAdd: {
    id: 0,
    username: '',
    password: '',
    email: '',
    phone: '',
    type: ''
  },
  dialogVisible: false,
  filtersOfEmail: [],
  selectIds: [],
  currentPage: 1,
  pageSize: 50,
  pageSizes: [50, 100, 150, 200, 250, 300, 350, 400, 450, 500, 550, 600, 650, 700, 750, 800, 850, 900, 950, 1000],
  total: 10
})
onMounted(
  async () => {
    // await

    await allUByPage()
    if (state.tableData.length > 0) {
      loading.value = false;
    }
  })

// 
const allUByPage = async () => {
  console.log("allUserByPage");
  await getUByPage(state.currentPage, state.pageSize)
  state.tableData = UsersRest.list
  state.total = UsersRest.total
  await fishFilter()
}
// 表格数据
const tableData = ref([] = state.tableData)
// 删除行
const deleteRow = async (index: number, id: number) => {
  await deleteU(id)
  console.log("deleteU(id),code.value", UsersCode);
  if (UsersCode === 0) {
    console.log("删了行", index);
    console.log("删了用户ID", id);
    tableData.value.splice(index, 1)//删除index行,1行
    await allUByPage()
    // deleteU(id)
    alert("删除成功")
    return
  } else {
    alert("删除失败")
    console.log("删除失败code.value", UsersCode);
    return
  }
}
// 动态添加行
const onAddItem = async (value: Boolean) => {
  console.log("传入判断", value);
  if (value) {
    console.log("用户输入le", state.userAdd);
    tableData.value.push(state.userAdd)
    state.userAdd = {
      id: 0,
      username: '',
      password: '',
      email: '',
      phone: '',
      type: ''
    }
  } else {
    alert("添加失败请重试")
  }
  await allUByPage()
}
//使当前行用户信息进入编辑模式
const getUpdatesUsers = (id: number) => {
  for (let i in state.tableData) {
    if (state.tableData[i].id == id) {
      state.userAdd.id = state.tableData[i].id
      state.userAdd.username = state.tableData[i].username
      state.userAdd.password = state.tableData[i].password
      state.userAdd.email = state.tableData[i].email
      state.userAdd.phone = state.tableData[i].phone
      state.userAdd.type = state.tableData[i].type
      console.log(state.userAdd);
      return
    }
  }
}
// 多选
const multipleSelection = ref<User[]>([])
const multipleTableRef = ref<InstanceType<typeof ElTable>>()
// 按钮多选
const toggleSelection = (rows?: User[]) => {
  if (rows) {
    rows.forEach((row) => {
      console.log("row", row);

      // TODO: improvement typing when refactor table
      // eslint-disable-next-line @typescript-eslint/ban-ts-comment

      multipleTableRef.value!.toggleRowSelection(row, undefined)
    })
  } else {
    multipleTableRef.value!.clearSelection()
  }
}
// 多选提示——选中数组编号
const handleSelectionChange = (val: User[]) => {
  multipleSelection.value = val
  console.log("多选选中的数据组", val);
  console.log("多选选中的数据组长度", val.length);
  // 将选中的行的id获取
  val.forEach(items => {
    state.selectIds.push(items.id)
  });
  // console.log("多选选中的数据组id", val[0].id);
}
// 筛选
const filterHandler = (
  value: string,
  row: User,
  column: TableColumnCtx<User>
) => {
  const property = column['property']
  console.log("筛选字段", property);
  console.log("筛选值", value);
  return row[property] === value
}
// 筛选内容刷新
async function fishFilter() {
  for (let i = 0; i < state.tableData.length; i++) {
    state.filtersOfEmail[i] = { text: state.tableData[i].email, value: state.tableData[i].email }
  }
  console.log("筛选OK");
  return
}
// 多彩行
const tableRowClassName = ({
  row,//行
  rowIndex,//
}: {
  row: User
  rowIndex: number
}) => {
  if (rowIndex % 2 == 0) {
    return 'warning-row'
  } else if (rowIndex % 3 == 0) {
    return 'success-row'
  }
  return ''
}
// 点击多选删除
const deletUsersByIds = async (ids: Number[]) => {
  if (ids.length <= 0) {
    alert("请选择删除内容")
    return
  }
  await deleteUIds(ids);
  if (UsersCode === 0) {
    alert("删除成功")
    await allUByPage()
  } else {
    alert("删除失败")
  }
}
// 加载等候loading
const loading = ref(true)
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
// 刷新ID按钮
const fishUId = async () => {
  await fish();
  await allUByPage()
  alert("刷新成功")
  return
}
// 自定义头部搜索框
const search = ref('')
const filterTableData = computed(() =>
  state.tableData.filter(
    (data) =>
      !search.value ||
      data.username.toLowerCase().includes(search.value.toLowerCase())
  )
)
// 分页
const currentPage2 = ref(state.currentPage)
const pageSize2 = ref(state.pageSize)
const small = ref(false)
const background = ref(true)
const disabled = ref(false)
const handleSizeChange = async (val: number) => {

  state.pageSize = val
  await allUByPage()
  console.log(`${val} items per page`)
}
const handleCurrentChange = async (val: number) => {
  state.currentPage = val
  await allUByPage()
  console.log(`current page: ${val}`)
}
// 表格布局
const tableLayout = ref('fixed')
</script>
<style>
.box{
  width: 100%;
  height: 100%;
}
.head {
  position: sticky;
  top: 10px;
}

.footer {
  position: sticky;
  bottom: 10px;
}

.el-table .warning-row {
  --el-table-tr-bg-color: var(--el-color-warning-light-9);
}

.el-table .success-row {
  --el-table-tr-bg-color: var(--el-color-success-light-9);
}
</style>
  