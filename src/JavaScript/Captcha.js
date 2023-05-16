import { getCaptcha, checkCoid } from '../api/CaptchaApi'
let CaptchaCode
let CaptchaMessage;
let CaptchaRest;
// export function getUrl() {
//     // const url = 'http://localhost:8088/lh.com/kaptcha'
//     const url = '/api/kaptcha'
//     return url
// }
export async function  getCaptchaImg(uuId){
   await getCaptcha(uuId).then((response) => {
        CaptchaCode=response.code
        CaptchaMessage=response.message
        CaptchaRest=response.rest
        return
    }).catch(error => {
        //发生错误时执行的代码
        console.log('异常响应')
        console.log(error)
    })
    return
}
export async function isCoid(uuId,userCode) {
   await checkCoid(uuId,userCode).then((response) => {
        console.log('验证码返回结果',response);
        CaptchaCode=response.code
        CaptchaMessage=response.message
        CaptchaRest=response.rest
        return
    }).catch(error => {
        //发生错误时执行的代码
        console.log('异常响应')
        console.log(error)
        return
    })
    return
}
export {CaptchaCode,CaptchaMessage,CaptchaRest}