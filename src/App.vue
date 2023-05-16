<template>
  <div class="items">
    <!-- <el-scrollbar>加了粘贴定位就失效 -->
    <el-container>
      <el-header class="MenuLR">
        <div>
          <AboutThisHeand />
        </div>
        <div v-if="$route.meta.keepAlive">
          <KeepAlive>
            <MenuLR />
          </KeepAlive>
        </div>
      </el-header>
      <el-main class="RouterView">
        <el-scrollbar>
          <RouterView />
        </el-scrollbar>
      </el-main>
      <el-footer class="Footer">
        <el-badge :value="state.shopNum" :max="99" class="left">
          <el-icon size="50px">
            <RouterLink to="">
              <Comment />
            </RouterLink>
          </el-icon>
        </el-badge>
        <el-icon size="50px" class="home">
          <RouterLink to="/">
            <House />
          </RouterLink>
        </el-icon>
        <el-badge :value="state.shopNum" :max="99" class="right">
          <el-icon size="50px">
            <RouterLink to="">
              <HomeFilled />
            </RouterLink>
          </el-icon>
        </el-badge>
      </el-footer>
      <BackTop />
    </el-container>
    <!-- </el-scrollbar> -->
  </div>
</template>
<script lang="ts" setup>
import { reactive } from 'vue';
import { useRoute, useRouter } from "vue-router"
import BackTop from './components/BackTop.vue';
import MenuLR from './components/MenuL&R.vue';
import AboutThisHeand from './views/AboutThisHeand.vue';
const state = reactive({
  shopNum: 0
})
const router = useRouter()
</script>

<style scoped>

.MenuLR {
  position: sticky;
  top: 0;
  /**
  left: 0;
  */
  z-index: 1000;
}

.left {
  position: absolute;
  left: 0;
}

.home {
  position: absolute;
  right: 50%;
  left: 50%;
}

.right {
  position: absolute;
  right: 5px;
}

.Footer {
  border-radius: 3%;
  width: 100%;
  height: 50px;
  position: fixed;
  left: 0px;
  bottom: 5px;
  margin: 0 auto;
  z-index: 60000;
  /*
  背景色反向
  */
  background-color: currentColor;
}
</style>