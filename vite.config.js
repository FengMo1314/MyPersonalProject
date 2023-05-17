import { fileURLToPath, URL } from 'node:url'
import { defineConfig,splitVendorChunkPlugin } from 'vite'
import vue from '@vitejs/plugin-vue'
import AutoImport from 'unplugin-auto-import/vite'
import Components from 'unplugin-vue-components/vite'
import { ElementPlusResolver } from 'unplugin-vue-components/resolvers'
// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue(),
  // el推荐按需导入插件1
  AutoImport({
    resolvers: [ElementPlusResolver()],
  }),
  // el推荐按需导入插件2
  Components({
    resolvers: [ElementPlusResolver()],
  }),
  splitVendorChunkPlugin(),
],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url)),
    },
  },
  define: {
    'process.env': process.env
  },
  // 打包配置
  build: {
    // target: 'modules',
    // outDir: "../webapp/",
    brotliSize:false,
    chunkSizeWarningLimit: 1500,
    emptyOutDir:false,
    rollupOptions: {
      output: {
        manualChunks(id) {
          if (id.includes('node_modules')) {
            return id.toString().split('node_modules')[1].split('/')[0].toString();
          }
        }
      }
    }
  },
  //本地服务
  server: {
    port:80,//端口
    open: false,  // 是否自动在浏览器打开
    https: false, // 是否开启 https
    // 跨域代理配置
    proxy: {
      '/api': {
        target: 'http://8.130.22.229:8086/lh.com/',
        // target:'http://localhost:8086/lh.com',
        changeOrigin: true,
        rewrite: (path) => {
          console.log(path);
          return path.replace(/^\/api/, '/')
        },
      }
    }
  },
})


