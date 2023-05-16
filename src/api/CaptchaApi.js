import http from '../utils/http'
// 获取验证码图片流

export function getCaptcha(uuId){
  return http.get('/api/kaptcha/'+uuId)
}
// 验证结果
export function checkCoid(uuId,userCode){
    return http.get('/api/kaptcha/'+uuId+'/'+userCode)
}