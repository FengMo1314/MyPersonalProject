<template>
    <div class="box">
        <el-scrollbar>
            <button>
                <RouterLink to="/home">
                    <el-icon>
                        <House />
                    </el-icon>VueHome
                </RouterLink>
            </button>
            <button>
                <RouterLink to="/aboutApi"><el-icon>
                        <Folder />
                    </el-icon>AboutAPI</RouterLink>
            </button>
            <button @click="toggleDark()">
                <i inline-block align-middle i="dark:carbon-moon carbon-sun" />
                <span v-if="!isDark">浅色<el-icon><Sunny /></el-icon></span>
                <span v-else>深色<el-icon><Moon /></el-icon></span>
            </button>
        </el-scrollbar>
    </div>
</template>
<script setup>
import { useDark, useToggle } from '@vueuse/core'
const isDark = useDark()
const toggleDark = useToggle(isDark)
</script>
<style scoped></style>