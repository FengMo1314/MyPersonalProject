<template>
  <!-- 用于布局的容器组件，方便快速搭建页面的基本结构：

<el-container>：外层容器。 当子元素中包含 <el-header> 或 <el-footer> 时，全部子元素会垂直上下排列， 否则会水平左右排列。

<el-header>：顶栏容器。

<el-aside>：侧边栏容器。

<el-main>：主要区域容器。

<el-footer>：底栏容器。 -->
  <div class="allListings">
    <el-main>
      <div class="header">
        <div class="select">
          <div class="inout">
            <input type="text" v-model="searchInputText" />
          </div>
          <div class="button">
            <el-button :icon="Search" size="large" @click="search">搜索</el-button>
          </div>
        </div>
        <!-- 填充率-1 -->
        <div>
          大页面越大越精彩:<el-slider v-model="fillRatio" />
        </div>
        <!--  填充率-2-->

      </div>
    </el-main>
    <el-main>
      <div class="body">
        <!-- 分页-1 -->
        <div class="demo-pagination-block">
          <el-scrollbar>
            <el-pagination v-model:current-page="currentPage4" :page-sizes="state.pageSizes" :small="small"
              :disabled="disabled" :background="background" layout="total,sizes,prev, pager, next, jumper"
              :total="state.total" @size-change="handleSizeChange" @current-change="handleCurrentChange"
              :hide-on-single-page="hideOnSinglePage" :page-count="pageCount" />
          </el-scrollbar>
        </div>
        <hr class="my-4" />
        <!-- 分页-2 -->
        <div class="body">
          <h1 v-if="state.inputMesg">以下展示有关您查询的：“{{ state.inputMesg }}”内容
            <h3 v-if="state.list.length <= 0">抱歉我们没有找到相关的</h3>
          </h1>
          <h1 v-else>没有输入查询条件显示全部</h1>
        </div>
      </div>
    </el-main>
    <!-- 房源简述-1 -->
    <el-main>
      <!-- 卡片-1 -->
      <el-space fill wrap :fill-ratio="fillRatio">
        <el-card v-for="item in state.list" class="box-card" :key="item.id">
          <template #header>
            <!-- <el-scrollbar> -->
            <div class="card-header">
              <div class="headname">
                <span>{{ item.name }}</span>
              </div>
              <el-button class="button" text>
                <a :href="item.listing_url">
                  马上预定
                </a>
              </el-button>
              <el-button @click="increase(item.id)">
                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-cart-plus"
                  viewBox="0 0 16 16">
                  <path d="M9 5.5a.5.5 0 0 0-1 0V7H6.5a.5.5 0 0 0 0 1H8v1.5a.5.5 0 0 0 1 0V8h1.5a.5.5 0 0 0 0-1H9V5.5z" />
                  <path
                    d="M.5 1a.5.5 0 0 0 0 1h1.11l.401 1.607 1.498 7.985A.5.5 0 0 0 4 12h1a2 2 0 1 0 0 4 2 2 0 0 0 0-4h7a2 2 0 1 0 0 4 2 2 0 0 0 0-4h1a.5.5 0 0 0 .491-.408l1.5-8A.5.5 0 0 0 14.5 3H2.89l-.405-1.621A.5.5 0 0 0 2 1H.5zm3.915 10L3.102 4h10.796l-1.313 7h-8.17zM6 14a1 1 0 1 1-2 0 1 1 0 0 1 2 0zm7 0a1 1 0 1 1-2 0 1 1 0 0 1 2 0z" />
                </svg>
                <el-icon size="20px">
                  <Plus />
                </el-icon>
              </el-button>
              <el-button @click="Subtracting(item.id)">
                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-cart-dash"
                  viewBox="0 0 16 16">
                  <path d="M6.5 7a.5.5 0 0 0 0 1h4a.5.5 0 0 0 0-1h-4z" />
                  <path
                    d="M.5 1a.5.5 0 0 0 0 1h1.11l.401 1.607 1.498 7.985A.5.5 0 0 0 4 12h1a2 2 0 1 0 0 4 2 2 0 0 0 0-4h7a2 2 0 1 0 0 4 2 2 0 0 0 0-4h1a.5.5 0 0 0 .491-.408l1.5-8A.5.5 0 0 0 14.5 3H2.89l-.405-1.621A.5.5 0 0 0 2 1H.5zm3.915 10L3.102 4h10.796l-1.313 7h-8.17zM6 14a1 1 0 1 1-2 0 1 1 0 0 1 2 0zm7 0a1 1 0 1 1-2 0 1 1 0 0 1 2 0z" />
                </svg>
                <el-icon size="20px">
                  <Minus />
                </el-icon>
              </el-button>
            </div>
          </template>

          <div class="picture">
            <a :href="item.picture_url">
              <!-- 
              'fill' | 'contain' | 'cover' | 'none' | 'scale-down'
             -->
              <el-image :src="item.picture_url" lazy :fit="fillRatio < 50 ? fit[0] : fit[1]" style="height: 100px" />
            </a>
          </div>
          <el-scrollbar>
            <div class="mesg" v-html="item.description" />
          </el-scrollbar>
          <el-scrollbar>
            <div class="host">
              <el-avatar :size="60" :src="item.host_thumbnail_url" @error="errorHandler">
                <img src="https://cube.elemecdn.com/e/fd/0fc7d20532fdaf769a25683617711png.png" />
              </el-avatar>
              <el-image />
              <span> <a :href="item.host_url">

                  查看房东详情</a>
              </span>
              <h3>name:{{ item.host_name }}</h3>
              <h4>location:{{ item.host_location }}</h4>
            </div>
          </el-scrollbar>
          <!-- </el-scrollbar> -->

        </el-card>
      </el-space>
    </el-main>
    <el-footer>
      <el-badge :value="state.shopNumb" :max="99" class="shop">
        <RouterLink :to="{path:'/shoppingcart',params:{'shops':shops}}" >
        <div >
          <i class="bi bi-cart"></i>
        </div>
      </RouterLink>
      </el-badge>
      
    </el-footer>
    <!-- 卡片-2 -->
    <el-footer class="el-footers">Footer</el-footer>
    <!--房源简述-2  -->
  </div>
</template>
<script setup lang="ts">
import { onMounted, reactive, ref, inject,provide } from 'vue'
// 
import { Search } from '@element-plus/icons-vue'
// 
import { ElScrollbar } from 'element-plus'
// 获取后端数据
import { allListingsByPage, ListingsCode, ListingsMessage, ListingsRest } from '../JavaScript/AllListings'
import ShoppingCart from './ShoppingCart.vue'
// 全局数据组
const state = reactive({
  total: 10,
  pageNum: 1,
  list: [],
  pageSize: 10,
  pageSizes: [10, 30, 50, 80, 100, 150, 200, 250, 300],
  inputMesg: false,
  shopNumb: 0
})
// 图像加载失败变量
const errorHandler = () => true
// 数据组结束
// 图片适应

// 'fill' | 'contain' | 'cover' | 'none' | 'scale-down'

const fit = ref(['fill', 'contain'])
const fillRatio = ref(30)
// 自定义大小结束
// 获取输入框数据
const searchInputText = ref(null)
function search() {
  // 输入框的数据
  const mesg = searchInputText.value
  console.log("输入", searchInputText.value);
  if (mesg != "" || mesg != null) {
    allListings()
  }
  state.inputMesg = mesg
}
// 获取数据框结束
// 计算分页-下脚
const currentPage4 = ref(state.pageNum)//当前页数
const small = ref(false)
const background = ref(true)
const disabled = ref(false)
const hideOnSinglePage = ref(true)
// const prevText=ref("返回上一页")
const pageCount = ref()
// const defaultCurrentPage=ref(2)
//每页显示条数
const handleSizeChange = (val: number) => {
  console.log(`${val} items per page`)
  state.pageSize = val
  allListings()
}
// 当前选择的页数
const handleCurrentChange = (val: number) => {
  console.log(`current page: ${val}`)
  state.pageNum = val
  allListings()
}
// 赋值数据
const allListings = async () => {
  await allListingsByPage(state.pageNum, state.pageSize, searchInputText.value)
  state.list = ListingsRest.list
  state.total = ListingsRest.total
}
// 购物车信息传递
const shops = ref([])

// 增加
const increase = (id) => {
  state.shopNumb++
  //第一种方法
  shops.value.push(id)
  console.log(id)
  clickChild()
  console.log(shops.value);
}
// 自减
const Subtracting = (id) => {
  if (state.shopNumb > 0) {
    state.shopNumb--
  } else {
    state.shopNumb = 0
  }
  clickChild()
}



// 注册一个回调函数，在组件挂载完成后执行。
onMounted(
  async () => {
    await allListings()
  })
// 
// 使用defineEmits创建名称，接受一个数组
const emit = defineEmits(['clickChild'])
const clickChild = () => {
  let param = {
    num: state.shopNumb
  }
  //传递给父组件
  emit('clickChild', param)
}
</script>
<style scoped>
/**
取消当前页面的默认滚动条
**/
::-webkit-scrollbar {
  display: none;
}

/*
购物车定位
*/
.shop {
  position: fixed;
  left: 5px;
  width: 50px;
  height: 50px;
  top: 50%;
  text-align: center;
  z-index: 100;
}

/**

*/
.demo-pagination-block {
  width: 100%;
  height: 100px;
  position: sticky;
  top: 10px;
}

.demo-pagination-block span {
  padding: 0;
}

.allListings {
  position: relative;
  min-height: 500px;
}

.header {
  position: relative;
  top: 0;
  margin: 15px auto;
}

.header .select {
  width: 45%;
  height: 80%;
  margin: 10px 0;
}

.header .select input {
  width: 100%;
  color: var(--text-color);
  background-color: var(--bg-color);
}

.header .select button {
  margin: 10px auto;
}

.body {
  position: sticky;
  text-align: center;
  background-color: var(--bg-color);
}

.headname {
  white-space: nowrap;
  text-overflow: ellipsis;
  overflow: hidden;
}

.box-card {
  width: 250px;
  height: 500px;
  overflow: auto;
}

.picture {
  overflow-y: auto;
}

.picture .el-image {
  display: block;
  min-height: 200px;
  margin-bottom: 10px;
}

.picture .el-image img {
  width: 100%;
  height: 100%;
}

.picture .el-image:last-child {
  margin-bottom: 0;
}

.mesg {
  height: 150px;
  width: 100%;
}

.el-footers {
  width: 100%;
  height: 100%;
  background-color: chocolate;
}</style>