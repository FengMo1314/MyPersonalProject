<template>
    <div class="demo-pagination-block">
        <div class="demonstration">All combined</div>
        <el-pagination
          v-model:current-page="currentPage4"
          :page-sizes="state.pageSizes"
          :small="small"
          :disabled="disabled"
          :background="background"
          layout="total, sizes, prev, pager, next, jumper"
          :total="state.total"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
        />
      </div>
</template>
<script lang="ts" setup>
import {} from 'vue'
import {defineComponent, onMounted, reactive, ref } from 'vue'
import { all } from '../JavaScript/AllListings'
import { list } from 'postcss';
// import { handleCurrentChange,handleSizeChange,g } from '../JavaScript/Pagination'
// 全局数据组
const state = reactive({
  total: 10,//总页数
  pageSizes:[1,2],//每页显示条数组
  pageNum:1,
  pageSize:10
})

const currentPage4 = ref(11)//当前页数
// const pageSize4 = ref(10)//当前页数
const small = ref(false)
const background = ref(true)
const disabled = ref(false)
//每页显示条数
const handleSizeChange = (val: number) => {
  console.log(`${val} items per page`)
  state.pageSize=val
}
// 当前选择的页数
const handleCurrentChange = (val: number) => {
  console.log(`current page: ${val}`)
  state.pageNum=val
}
// 
//接收父组件传过来的值,需要注意defineProps只能在setup语法糖里面使用，不需要import引入
const yyyy = defineProps({
	fatherTitle:{
		type:String,//类型字符串
		default:'当前Child.vue的默认值'//如果没有传递msg参数,默认值是这个
	},
	fatherMoney:{
		// type:Number,//类型字符串
    type:Object,
    // type:String,
		default:null//如果没有传递msg参数,默认值是这个
	},
	fatherWifi:{
		type:Object,//类型字符串
		default:{id:999}//如果没有传递msg参数,默认值是这个
	},
	myEmptyStr:{
		type:String,
		default:"myEmptyStr默认值为空字符串"
	},
	fatherArr:{
		type:Object,//类型字符串
		//default:[]//如果没有传递msg参数,默认值是这个
	},
})
console.log(yyyy.fatherMoney.total);
state.total=yyyy.fatherMoney.total;
state.pageNum=yyyy.fatherMoney.pageNum
state.pageSize=yyyy.fatherMoney.pageSize
</script>