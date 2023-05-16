<template>
  
  <el-menu default-active="2" class="el-menu-vertical-demo" :collapse="dataOfUsers.isCollapse" @open="handleOpen"
    @close="handleClose" @select="handleSelect">
    <el-sub-menu index="1">
      <template #title>
        <el-icon>
          <location/>
        </el-icon>
        <span>信息管理</span>
      </template>
      <el-menu-item-group>
        <template #title><span>个人信息</span></template>
        <el-menu-item index="1-1">注册信息详情</el-menu-item>
        <el-menu-item index="1-2">账号信息详情</el-menu-item>
      </el-menu-item-group>
      <el-menu-item-group title="历史操作信息">
        <el-menu-item index="1-3">历史登录信息</el-menu-item>
        <el-menu-item index="1-4">历史文件信息</el-menu-item>
      </el-menu-item-group>
      <el-sub-menu index="1-5">
        <template #title><span>实验</span></template>
        <el-menu-item index="1-4-1">item one</el-menu-item>
      </el-sub-menu>
    </el-sub-menu>
    <el-menu-item index="2">
      <el-icon><icon-menu /></el-icon>
      <template #title>Navigator Two</template>
    </el-menu-item>
    <el-menu-item index="3" disabled>
      <el-icon>
        <document />
      </el-icon>
      <template #title>文本执行列表</template>
    </el-menu-item>
    <el-menu-item index="4">
      <el-icon>
        <setting />
      </el-icon>
      <template #title>更多设置</template>
    </el-menu-item>
  </el-menu>
</template>
<script lang="ts" setup>

import {
  Document,
  Menu as IconMenu,
  Location,
  Setting,
} from '@element-plus/icons-vue'
// 子组件接受父组件传参
// 接收父组件传递过来的值
const dataOfUsers = defineProps({
  isCollapse: Boolean
})
// const isCollapse = ref(true)
const handleOpen = (key: string, keyPath: string[]) => {
  console.log("handleOpen:", key, keyPath)
}
const handleClose = (key: string, keyPath: string[]) => {
  console.log('handleClose', key, keyPath)
}
// 定义要发送的emit事件
const emit = defineEmits(['handleSelect'])
const handleSelect = (key: string, keyPath: string[]) => {
  console.log('多级监控',key, keyPath)
  emit('handleSelect',key,keyPath)
}
</script>
  
<style scoped>
.el-menu-vertical-demo{
  z-index: 1000000;
}

.el-sub-menu {
  
  border-radius: 10% 10% 0 0;
  /*
  margin: 20px 0;
  */
}

.el-menu {
  border-right: none;
  border-radius: 10%;
  }

.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 200px;
  min-height:500px;
}
</style>