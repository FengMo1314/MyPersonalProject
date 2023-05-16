<template>
    <div class="common-layout">
        <el-container>
            <el-aside class="rigitMenu-box">
                <el-scrollbar>
                    <el-row class="tac">
                        <el-col :span="24">
                            <el-menu :default-active="state.key" class="el-menu-vertical-demo" @open="handleOpen"
                                @close="handleClose" @select="handleSelect">
                                <el-sub-menu index="1">
                                    <template #title>
                                        <el-icon>
                                            <location />
                                        </el-icon>
                                        <span>步骤说明</span>
                                    </template>
                                    <el-menu-item-group title="Group One">
                                        <el-menu-item index="1-1">item one</el-menu-item>
                                        <el-menu-item index="1-2">item two</el-menu-item>
                                    </el-menu-item-group>
                                    <el-menu-item-group title="Group Two">
                                        <el-menu-item index="1-3">item three</el-menu-item>
                                    </el-menu-item-group>
                                    <el-sub-menu index="1-4">
                                        <template #title>item four</template>
                                        <el-menu-item index="1-4-1">item one</el-menu-item>
                                    </el-sub-menu>
                                </el-sub-menu>
                                <el-menu-item index="2">
                                    <el-icon><i class="bi bi-eye-fill"></i></el-icon>
                                    <span>商品类型管理</span>
                                </el-menu-item>
                                <!-- disabled——禁用 -->
                                <el-menu-item index="3">
                                    <el-icon>
                                        <i class="bi bi-bag-fill"></i>
                                    </el-icon>
                                    <span>商品管理</span>
                                </el-menu-item>
                                <el-menu-item index="4" v-if="disabledmenu()">
                                    <el-icon>
                                        <i class="bi bi-diagram-3-fill"></i>
                                    </el-icon>
                                    <span>部门管理</span>
                                </el-menu-item>
                                <el-menu-item index="5" v-if="disabledmenu()">
                                    <el-icon>
                                        <i class="bi bi-person-square"></i>
                                    </el-icon>
                                    <span>管理员管理</span>
                                </el-menu-item>
                                <el-menu-item index="6" v-if="disabledmenu()">
                                    <el-icon>
                                        <i class="bi bi-person-fill"></i>
                                    </el-icon>
                                    <span>用户管理</span>
                                </el-menu-item>
                            </el-menu>
                        </el-col>
                    </el-row>
                </el-scrollbar>
            </el-aside>
            <el-main>
                <div v-if="state.key == '2'" >
                    <ShopType />
                </div>
                <div v-else-if="state.key == '3'" >
                    <Shop/>
                </div>
                <div v-else-if="state.key == '4'" >
                    <Dept />
                </div>
                <div v-else>
                    <el-empty :image-size="200" />
                </div>
            </el-main>
        </el-container>
    </div>
</template>
<script lang="ts" setup>
import { reactive } from 'vue'
import ShopType from './src/ShopType.vue';
import Dept from './src/Dept.vue';
import Shop from './src/Shop.vue';
const state = reactive({
    key: '1'
})
import {
    Document,
    Menu as IconMenu,
    Location,
    Setting,
} from '@element-plus/icons-vue'
const handleSelect = (key: string, keyPath: string[]) => {
    console.log('多级监控', key, keyPath)
    state.key = key
}
const handleOpen = (key: string, keyPath: string[]) => {

    console.log(key, keyPath)
}
const handleClose = (key: string, keyPath: string[]) => {
    console.log(key, keyPath)
}
// 
const type = window.sessionStorage.getItem('type')
// 菜单权限
const disabledmenu = () => {
    if (type === '1') {
        return true
    }
    return false
}
</script>
<style scoped>
.common-layout .el-aside {
    width: 150px;
}

.common-layout .el-aside .el-sub-menu,
.el-menu-item {
    width: 150px;
}

.common-layout .el-main {
    padding: 0px;
}</style>