<template>
  <!-- <el-container>：外层容器。 当子元素中包含 <el-header> 或 <el-footer> 时，全部子元素会垂直上下排列， 否则会水平左右排列。

    <el-header>：顶栏容器。
    
    <el-aside>：侧边栏容器。
    
    <el-main>：主要区域容器。
    
    <el-footer>：底栏容器。 -->
  <!-- EL上中下布局 -->
  <!-- <div class="common-layout"> -->
  <el-container>
    <el-aside class="rigitMenu-box">
      <el-scrollbar>
        <!-- <el-container class="m"> -->
        <el-header class="radio-group">
          <el-radio-group v-model="isCollapse">
            <el-radio-button :label="false">展开</el-radio-button>
            <el-radio-button :label="true">折叠</el-radio-button>
          </el-radio-group>
        </el-header>
        <!-- 侧边布局 -->
        <el-aside class="rigitMenu">
          <!-- 滚动条 -->
          <el-scrollbar>
            <!-- 侧边折叠菜单 -->
            <Collapse :isCollapse="isCollapse" @handleSelect="witchKey" />
          </el-scrollbar>
        </el-aside>
        <!-- </el-container> -->
      </el-scrollbar>
    </el-aside>
    <!-- 主内容展示区 -->
    <el-main class="main">
      <!-- 滚动条-1 -->
      <!-- <el-scrollbar> -->
        <div v-if="state.key == '1-1'">
          <el-scrollbar>
            <User />
          </el-scrollbar>
        </div>
        <div v-else-if="state.key == '1-2'">
          <el-scrollbar>
            <Upload />
          </el-scrollbar>
        </div>
        <div v-else>
          <!-- 空状态 -->
          <el-scrollbar>
            <el-empty description="description" />
          </el-scrollbar>
        </div>
      <!-- </el-scrollbar> -->
      <!-- 滚动条-2 -->
    </el-main>
  </el-container>
  <!-- </div> -->
</template>
  
<script lang="ts" setup>
import Collapse from './Collapse.vue';
import Upload from './Upload.vue';
import User from './User.vue';
import { ref, onMounted, reactive } from 'vue'
const state = reactive({
  key: ''
})

// 监控子组件传来的菜单选项
const witchKey = (key: string, keyPath: string[]) => {
  state.key = key
}
const isCollapse = ref(true)
</script>
  
<style scoped>
/**
.radio-group {

  background-color: blueviolet;
}
*/
.rigitMenu-box {
  /*
  background-color: aquamarine;
  */
  position:sticky;
  left: 0;
}

.rigitMenu {/**
  background-color: coral;
  */
  min-height: 100%;
}
</style>
  