import { request } from './request'
const http = {
  get(url, params) {
    const config = {
      method: 'get',
      url: url,
    }
    // 如果参数不为空,则将参数复制给config的参数列表
    if (params) config.params = params

    return request(config)
  },
  post(url, params,Type) {
    const config = {
      method: 'post',
      url: url,
    }
    if (Type==='1') {
      config.transformRequest = [function (data, headers) {
        // 去除post请求默认的Content-Type
        delete headers['Content-Type']
        return data
      }]
    } 
    if (params) config.data = params
    return request(config)
  },
  put(url, params) {
    const config = {
      method: 'put',
      url: url,
    }

    if (params) config.params = params
    return request(config)
  },
  delete(url, params) {
    const config = {
      method: 'delete',
      url: url
    }
    if (params) config.params = params
    return request(config)
  }
}
export default http
