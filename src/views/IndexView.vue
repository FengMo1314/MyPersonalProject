<template>
    <!-- EL上中下布局 -->
    <!-- <div class=""> -->
    <!-- 布局容器 -->
    <!-- <el-container > -->
    <!-- 主容器 -->
    <el-main>
        <Carousel class="Carousel" />
    </el-main>
    <el-main>
        <AllListings />
    </el-main>
    <el-footer class="test">
        <h1>底部</h1>
    </el-footer>
    <!-- 回到顶部 -->
    <BackTop />
    <!-- </el-container> -->
    <!-- </div> -->
</template>
<script setup>
import Carousel from '../components/Carousel.vue';
import AllListings from '../components/AllListings.vue';
import BackTop from '../components/BackTop.vue'
//
</script>
<style>
.main {
    min-width: 100%;
    margin-top:5%;
}
</style>