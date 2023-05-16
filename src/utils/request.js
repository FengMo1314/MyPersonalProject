/**
 * sessionStorage.setItem("key","value");//以“key”为名称存储一个值“value”
 * sessionStorage.getItem("key");//获取名称为“key”的值
 * sessionStorage.removeItem("key");//删除名称为“key”的信息。
 * sessionStorage.clear();​//清空sessionStorage中所有信息
 */
import axios from 'axios'
import router from "../router/index";
export function request(config) {
  // 创建axios的实例
  const instance = axios.create({
    baseURL: 'http:///',
    // baseURL: 'http://8.130.22.229',
    // baseURL: 'http://localhost:8086/lh.com/',
    timeout: 20000
  })
  // instance.defaults.baseURL="http://localhost/lh.com/"
  // 请求拦截器配置
  instance.interceptors.request.use(config => {
    // `headers` 是即将被发送的自定义请求头
    // headers: {"X-Requested-With": "XMLHttpRequest"},
    // 获取本地token,并将其复制给请求头部的Authoritization
    config.headers.Authorization = window.sessionStorage.getItem('token')
    console.log("config.headers.Authorization", config.headers.Authorization);
    axios.defaults.headers.put['Content-Type'] = 'application/x-www-form-urlencoded';
    return config
  }, error => {
    console.log("请求拦截器配置:error" + error)
    return Promise.error(error)
  })
  // 响应拦截器配置
  instance.interceptors.response.use(response => {
    console.log("响应拦截器配置:response", response)
    console.log("响应拦截器配置:return response.data", response.data);
    if (response.data.code == 20001) {
      alert(response.data.message)
      router.push({ path: '/' })
    } else if (response.data.code == 20002||response.data.code == 20004) {//2002——登录过期;2004——无效
      alert(response.data.message)
      // sessionStorage.clear()//清除本地所有储存
      router.push({ path: '/login' })
    }else if(response.data.code==20003){//20003——无权访问
      alert(response.data.message)
      // router.push({ path: '/' })
    }else if(response.data.code==-1){
      router.push({ path: '/404' })
    }
   
    return response.data
  }, error => {
    console.log("响应拦截器配置:选择error.response状态码", error.response);
    if (error.response) {
      // 请求已发出，但服务器响应的状态码不在 2xx 范围内
      console.log('error.response.data:\t',error.response.data);
      console.log('error.response.status:\t',error.response.status);
      console.log('error.response.headers',error.response.headers);
    } else {
      if(error.message==='timeout of 20000ms exceeded'){
        alert('网络超时')
      }
      // Something happened in setting up the request that triggered an Error
      console.log('Error', error.message);
    }
    console.log(error.config);
    switch (error.response.status) {
      case 401:
        console.log("无权访问")
        // router.push({ path: '/login' })
        break
      case 403:
        console.log("token过期啦")
        // router.push({ path: '/login' })
        break
      case 404:
        console.log("404啦")
        // router.push({ path: '/404' })
        break
      default:
        console.log("响应拦截器配置:选择error.response.status状态码,其他状态码");

        return Promise.reject(error)
    }
    return Promise.reject(error)
  })
  // 发送真正的网络请求
  return instance(config);
}
export default request
