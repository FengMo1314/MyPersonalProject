<template>
  <div class="scrollbar-flex-content">
    <el-scrollbar>
      <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal"
        :ellipsis="windowWidth <= 500 ? true : false" @select="handleSelect" background-color="#545c64" text-color="#fff"
        active-text-color="#ffd04b">

        <el-menu-item index="0" class="item-ico">
          <RouterLink to="/">
            <img src="../../public/com-logo1.jpg" alt="" class="log">
          </RouterLink>
        </el-menu-item>
        <el-menu-item class="and">
          <h1>&</h1>
        </el-menu-item>
        <RouterLink to="/userCenter">
          <el-menu-item class="avtar" index="1-1" v-if="imgUrl == '' || imgUrl == null">
            <el-avatar src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png" />
          </el-menu-item>
          <el-menu-item class="avtar" index="1-1" v-else>
            <el-avatar :src="imgUrl" />
          </el-menu-item>
        </RouterLink>
        <div class="flex-grow" />
        <el-menu-item index="1">站点主要</el-menu-item>
        <el-sub-menu index="2" v-if="token === '' || token === null">
          <template #title>登录/注册</template>
          <RouterLink to="/login">
            <el-menu-item index="2-1">登录</el-menu-item>
          </RouterLink>
          <RouterLink to="/register">
            <el-menu-item index="2-2">注册</el-menu-item>
          </RouterLink>
        </el-sub-menu>
        <el-sub-menu index="2" v-else>
          <template #title>个人相关</template>
          <div class="ico">
            <RouterLink to="/userCenter">
              <el-menu-item index="2-1" v-if="imgUrl == ''|| imgUrl==null">
                <el-avatar src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png" />
              </el-menu-item>
              <el-menu-item index="2-1" v-else>
                <el-avatar :src="imgUrl"/>
              </el-menu-item>
            </RouterLink>
          </div>
          <RouterLink to="/userCenter">
            <el-menu-item index="2-2">账号信息</el-menu-item>
          </RouterLink>
          <el-menu-item index="2-3">
            <el-icon>
              <setting />
            </el-icon>
            设置
          </el-menu-item>
          <el-menu-item index="2-4">退出登录(LoginOut)</el-menu-item>
          <el-sub-menu index="2-5">
            <template #title>更多</template>
            <el-menu-item index="2-5-1"></el-menu-item>
            <el-menu-item index="2-5-2">item two</el-menu-item>
            <el-menu-item index="2-5-3">item three</el-menu-item>
          </el-sub-menu>
        </el-sub-menu>
        <!-- <p v-if="userType == 'root' && token != '' && token != null"> -->
        <!-- <div v-if="userType=='root'"> -->
        <el-sub-menu index="3" v-if="userType == 'root' && token != '' && token != null">
          <template #title>管理员操作</template>
          <RouterLink to="/users">
            <el-menu-item index="3-1">用户表</el-menu-item>
          </RouterLink>
          <RouterLink to="/carousels">
            <el-menu-item index="3-2">走马灯</el-menu-item>
          </RouterLink>
          <el-menu-item index="3-3">item three</el-menu-item>
          <el-sub-menu index="3-4">
            <template #title>item four</template>
            <el-menu-item index="3-4-1">item one</el-menu-item>
            <el-menu-item index="3-4-2">item two</el-menu-item>
            <el-menu-item index="3-4-3">item three</el-menu-item>
          </el-sub-menu>
        </el-sub-menu>
        <el-sub-menu index="万和管理员">
          <template #title>万和管理员专区</template>
          <RouterLink to="/root/login" v-if="token == '' || token == null">
            <el-menu-item index="万和管理员登录">
              登录
            </el-menu-item>
          </RouterLink>
          <el-menu-item index="万和管理员首页" v-else v-if="(userType=='0' || userType =='1')">后台首页</el-menu-item>
          <el-menu-item index="4-2">item three</el-menu-item>
          <el-sub-menu index="4-4">
            <template #title>item four</template>
            <el-menu-item index="4-4-1">item one</el-menu-item>
            <el-menu-item index="4-4-2">item two</el-menu-item>
            <el-menu-item index="4-4-3">item three</el-menu-item>
          </el-sub-menu>
        </el-sub-menu>
        <el-sub-menu index="5">
          <template #title>开发中5</template>
          <el-menu-item index="5-1">item one</el-menu-item>
          <el-menu-item index="5-2">item two</el-menu-item>
          <el-menu-item index="5-3">item three</el-menu-item>
          <el-sub-menu index="5-4">
            <template #title>item four</template>
            <el-menu-item index="5-4-1">item one</el-menu-item>
            <el-menu-item index="5-4-2">item two</el-menu-item>
            <el-menu-item index="5-4-3">item three</el-menu-item>
          </el-sub-menu>
        </el-sub-menu>
        <el-sub-menu index="6">
          <template #title>开发中6</template>
          <el-menu-item index="6-1">item one</el-menu-item>
          <el-menu-item index="6-2">item two</el-menu-item>
          <el-menu-item index="6-3">item three</el-menu-item>
          <el-sub-menu index="6-4">
            <template #title>item four</template>
            <el-menu-item index="6-4-1">item one</el-menu-item>
            <el-menu-item index="6-4-2">item two</el-menu-item>
            <el-menu-item index="6-4-3">item three</el-menu-item>
          </el-sub-menu>
        </el-sub-menu>
        <!-- </p> -->
        <!-- </div> -->
      </el-menu>
    </el-scrollbar>
  </div>
</template>
  
<script lang="ts" setup>
import { ref, onMounted, watch, reactive } from 'vue'
import { loginOutByuser, LoginOutCode, LoginOutMessage } from '../JavaScript/LoginOut'
import { useRouter } from 'vue-router';
import {
  Setting,
} from '@element-plus/icons-vue'
const router = useRouter()
const activeIndex = ref('1')
const state = reactive({
})
// 监控菜单的选项下标
const handleSelect = async (key: string, keyPath: string[]) => {
  if (key === '2-3') {
    router.push('/setting')
  } else if (key === '2-4') {
    await loginOutByuser()
    if (LoginOutCode == 0) {
      alert("退出成功")
    }else{
      alert(LoginOutMessage)
    }
    window.sessionStorage.clear()//清空所有缓存
      await router.push("/")//跳转到首页
      location.reload()//刷新
  }else if(key==='万和管理员首页'){
    router.push('/root')
  }
  console.log("标签主键", key, keyPath);
}
const imgUrl = window.sessionStorage.getItem('imgUrl')
const token = window.sessionStorage.getItem('token')
const userType = window.sessionStorage.getItem('type')
// const ellipsis = ref(false)
onMounted(
  () => {
    getWindowResize()
    window.addEventListener('resize', getWindowResize)
  }
)
// 获取屏幕尺寸
// 屏幕宽度
const windowWidth = ref(0)
// 屏幕高度
const windowHeight = ref(0)
const getWindowResize = function () {
  windowWidth.value = window.innerWidth
  windowHeight.value = window.innerHeight
}
</script>
  
<style scoped>
.avtar,
.and {
  margin: auto 0;
}

.item-ico {
  position: sticky;
  top: 0;
  left: 0;
  z-index: 10000;
}

.flex-grow {
  flex-grow: 1;
}

.ico {
  height: 40px;
  size: 0vw;
  margin: 2px auto;
}

.log {
  width: 70px;
  height: 50px;
  border-radius: 50%;
}
</style> 
  