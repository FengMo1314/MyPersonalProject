import { createRouter, createWebHistory } from 'vue-router'
import IndexView from '../views/IndexView.vue'
import LoginView from '../views/LoginView.vue'
import RegisterView from '../views/RegisterView.vue'
import VueHomeView from '../views/VueHomeView.vue'
import AboutThisHeand from '../views/AboutThisHeand.vue'
const router = createRouter({
  // history: createWebHistory('vue'),
  // routes: constantRoutes
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
  
    {
      path: '/aboutThisHeade',
      name: 'AboutThisHeand',
      component: AboutThisHeand,
      hidden: true,
      meta: {
        keepAlive: false,
        title: '顶栏'
      }

    },
    {
      path: '/home',
      name: 'vueHome',
      component: VueHomeView,
      hidden: true,
      meta: {
        keepAlive: false,
        title: 'vue首页'
      }

    },
    {
      path: '/',
      name: 'index',
      component: IndexView,
      hidden: true,
      meta: {
        keepAlive: true,
        title: '商城首页'
      }
    },
    {
      path: '/login',
      name: 'login',
      component: LoginView,
      hidden: true,
      meta: {
        keepAlive: false,
        title: '登录'
      }

    },
    {
      path: '/register',
      name: 'register',
      component: RegisterView,
      hidden: true,
      meta: {
        keepAlive: false,
        title: '注册'
      }

    },
    {
      path: '/aboutApi',
      name: 'aboutApi',
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import('../views/AboutApiView.vue'),
      hidden: true,
      meta: {
        keepAlive: false,
        title: 'AboutAPI'
      }

    },
    {
      path: '/404',
      name: '404',
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import('../components/NoFinde.vue'),
      hidden: true,
      meta: {
        keepAlive: false,
        title: '404'
      }

    },
    {
      path: '/users',
      name: 'users',
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import('../components/Users.vue'),
      hidden: true,
      meta: {
        keepAlive: false,
        title: '管理员用户表'
      }

    },
    {
      path: '/userCenter',
      name: 'userCenter',
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import('../components/UserCenter.vue'),
      hidden: true,
      meta: {
        keepAlive: false,
        title: '用户中心'
      }

    },
    {
      path: '/carousels',
      name: 'carousels',
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import('../components/Carousels.vue'),
      hidden: true,
      meta: {
        keepAlive: true,
        title: '走马灯'
      }

    },
    {
      path: '/setting',
      name: 'setting',
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import('../components/Setting.vue'),
      hidden: true,
      meta: {
        keepAlive: false,
        title: '用户设置'
      }

    },
    {
      path: '/shoppingcart',
      name: 'shoppingcart',
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import('../components/ShoppingCart.vue'),
      hidden: true,
      meta: {
        keepAlive: false,
        title: '用户设置'
      }

    },
    {
      path: '/root',
      name: 'root',
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import('../root-views/index.vue'),
      // hidden: true,
      // meta: {
      //   keepAlive: false,
      //   title: '管理员首页'
      // },
     children:[
      
     ]
    },
    {
      path:'/root/login',
      name:'root-login',
      component:()=>import('../root-views/login.vue')
    }
  ]
})
// 导航守卫
// 使用 router.beforeEach 注册一个全局前置守卫，判断用户是否登陆
router.beforeEach((to, from, next) => {
  // to 将要访问的路径
  // from 从哪里跳转来
  // next 放行
  //跳转到登录页面直接放行
  if (to.matched.length === 0) {  // 如果未匹配到路由
    from.name ? next({ name: from.name }) : next({ path: '/' })
  } else {
    if (to.path === '/home') {
      next()

    }
    else if (to.path === '/login'|| to.path=='/root/login') {
      next()
    }
    else if (to.path === '/index.html') {
      next()

    }
    else if (to.path === "/aboutApi") {
      next()

    } else if (to.path == '/') {
      next()
    } else if (to.path == '/register' || to.path == '/404') {
      next()

    } else {
      // 获取token,看是否有token,有token放行
      const token = window.sessionStorage.getItem("token")
      if (token == '' || token == null) {
        next({ path: '/login' })
      }
      // 放行
      next()
    }
  }
});
export default router
