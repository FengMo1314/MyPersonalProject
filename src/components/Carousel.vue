<template>
  <el-main>
    <el-carousel :interval="2000" type="card" height="200px">
      <el-carousel-item v-for="item in state.list" :key="item.id">
        <span>{{ item.id }}</span>
        <img :src="item.url" alt="">
      </el-carousel-item>
    </el-carousel>
  </el-main>
</template>
<script setup>
import { onMounted, reactive, ref } from 'vue'
import { allCarousels, CarouselsRest, CarouselsCode, getOneCarouselsByUrl, addOneCarousels } from '../JavaScript/Carousel'
// 全局数据组
const state = reactive({
  list: [
    {
      id: '1',
      url: 'https://tse1-mm.cn.bing.net/th/id/OIP-C.oX4T0LU1Civ6PI0J-tZyLgHaEK?pid=ImgDet&rs=1'
    },
    {
      id: '2',
      url: 'https://tse1-mm.cn.bing.net/th/id/OIP-C.oX4T0LU1Civ6PI0J-tZyLgHaEK?pid=ImgDet&rs=1'
    },
    {
      id: '3',
      url: 'https://tse1-mm.cn.bing.net/th/id/OIP-C.oX4T0LU1Civ6PI0J-tZyLgHaEK?pid=ImgDet&rs=1'
    }
  ]
})
onMounted(
  async () => {
     await allCarousels()
    state.list = CarouselsRest
  }
)


</script>
<style scoped>
.el-carousel__item img {
  width: 100%;
  height: 100%;
}

.el-carousel__item span {
  position: relative;
  top: 0;
  left: 0;
}

.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n + 1) {
  background-color: #d3dce6;
}
</style>
  