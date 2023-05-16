<template>
  <div style="margin-top: 20px">
    <el-button @click="delectByIds(state.ids)">批量删除</el-button>
    <el-button @click="toggleSelection()">清除全选</el-button>
  </div>
  <el-table ref="multipleTableRef" :data="state.tableData" style="width: 100%" @selection-change="handleSelectionChange"
    height="500">
    <el-table-column type="selection" width="100" fixed />
    <el-table-column property="id" label="id" fixed width="100" />
    <el-table-column property="url" label="网络地址" />
    <el-table-column fixed="right" label="Operations" width="120">
      <template #default="scope">
        <el-button link type="primary" size="small" @click="handleClick(scope.row.id)">删除</el-button>
        <el-button link type="primary" size="small"
          @click="centerDialogVisible = true; updateDo(scope.row.id, scope.row.url)">修改</el-button>
      </template>
    </el-table-column>
  </el-table>
  <!-- 弹出框 -->
  <el-dialog v-model="centerDialogVisible" title="Warning" width="30%" align-center>
    <el-form
    :label-position="labelPosition"
    label-width="100px"
    :model="formLabelAlign"
    style="max-width: 460px"
  >
    <el-form-item label="ID">
      <el-input v-model="formLabelAlign.Id" readonly="true"/>
    </el-form-item>
    <el-form-item label="URL">
      <el-input v-model="formLabelAlign.Url" />
    </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="centerDialogVisible = false">Cancel</el-button>
        <el-button type="primary" @click="updateUrl()">
          Confirm
        </el-button>
      </span>
    </template>
  </el-dialog>
</template>
  
<script lang="ts" setup>
import { onMounted, reactive, ref, computed } from 'vue'
import type { ElTable, TableColumnCtx } from 'element-plus'
import { allCarousels, getOneCarouselsByUrl, CarouselsCode, CarouselsMessage, CarouselsRest, deleteOneCarouselsById, deleteSomeCarouselsByIds,updateOneCarouselsById } from '../JavaScript/Carousel'
interface Carousels {
  id: number
  url: string
}
const state = reactive({
  tableData: <Carousels[]>[],
  ids: [],
  addData: {}
})
const labelPosition = ref('top')

const formLabelAlign = reactive({
  Id: 0,
  Url: '',
})
onMounted(
  async () => {
    // await
    await allCarousels()
    state.tableData = CarouselsRest
    console.log(CarouselsRest, "CarouselsRest");
  })
const multipleTableRef = ref<InstanceType<typeof ElTable>>()
const multipleSelection = ref<Carousels[]>([])
const toggleSelection = (rows?: Carousels[]) => {
  if (rows) {
    rows.forEach((row) => {
      // TODO: improvement typing when refactor table
      // eslint-disable-next-line @typescript-eslint/ban-ts-comment
      multipleTableRef.value!.toggleRowSelection(row, undefined)
    })
  } else {
    multipleTableRef.value!.clearSelection()
  }
}
// 多选框
const handleSelectionChange = (val: Carousels[]) => {
  val.forEach(items => {
    state.ids.push(items.id)
  });
  multipleSelection.value = val
}
const tableData = ref<Carousels[]>(state.tableData)
// 清除
const handleClick = async (id: number) => {
  await deleteOneCarouselsById(id)
  if (CarouselsCode == 0) {
    await allCarousels()
    state.tableData = CarouselsRest
  }
  alert(CarouselsMessage)
}
// 批量删除
const delectByIds = async (ids) => {
  await deleteSomeCarouselsByIds(ids)
  if (CarouselsCode == 0) {
    await allCarousels()
    state.tableData = CarouselsRest
  }
  alert(CarouselsMessage)
}
// 进入编辑模式
const updateDo = async (id: number, url: string) => {
  formLabelAlign.Id = id;
  formLabelAlign.Url = url
}
const updateUrl = async () => {
  await updateOneCarouselsById({ 'id': formLabelAlign.Id, 'url': formLabelAlign.Url })
  if (CarouselsCode === 0) {
    await allCarousels()
    state.tableData = CarouselsRest
    formLabelAlign.Id = 0
    formLabelAlign.Url = ''
    centerDialogVisible.value = false
  }
  alert(CarouselsMessage)
}
const centerDialogVisible = ref(false)
</script>
<style scoped>
.dialog-footer button:first-child {
  margin-right: 10px;
}
</style>  