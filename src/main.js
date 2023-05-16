import { createApp } from 'vue'
import App from './App.vue'
import Router from './router'
import './assets/main.css'
// 完整引入#
// 如果你对打包后的文件大小不是很在乎，那么使用完整导入会更方便。
// import ElementPlus from 'element-plus'
// import 'element-plus/dist/index.css'
// main.ts
// 如果只想导入css变量——黑暗模式
import 'element-plus/theme-chalk/dark/css-vars.css'
// 导入PC端自适应不同分辨率电脑依赖-px->rem
// import "lib-flexible-computer"
// DataV
// import DataV from '@dataview/datav-vue3';
// import Axios from 'axios'
// import VueAxios from 'vue-axios'
// 如果您正在使用CDN引入，请删除下面一行。
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
const app = createApp(App)
app.use(Router)
// app.use(ElementPlus)
// app.use(Axios)
// app.use(VueAxios)
//这句不写, 组件里无法注入全局axios, 也就无法使用
// app.provide('axios', app.config.globalProperties.axios) 
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
  app.component(key, component)
}
app.mount('#app');
