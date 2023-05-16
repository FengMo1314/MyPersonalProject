import {loginOut} from '../api/LoginOutApi'
let LoginOutCode;
let LoginOutMessage;
let LoginOutRest;
export async function loginOutByuser(){
  await  loginOut().then((response)=>{
      LoginOutCode=response.code
      LoginOutMessage=response.message
      LoginOutRest=response.rest
      return {LoginOutCode,LoginOutMessage,LoginOutRest}
    }).catch(error=>{
    //发生错误时执行的代码
    console.log('异常响应')
    console.log(error)
    })
    // return {LoginOutCode,LoginOutMessage,LoginOutRest}
}
export {LoginOutCode,LoginOutMessage,LoginOutRest}