import {register,usernameExit} from '../api/RegisterApi'
let RegisterCode;
let RegisterMessage;
let RegisterRest;
export async function registers(mesg){
  await  register({'username':mesg.username,'password':mesg.password,'type':mesg.userType}).then((response)=>{
    RegisterCode=response.code
    RegisterMessage=response.message
    RegisterRest=response.rest
    if(RegisterCode==0){
        // 存入token
        window.sessionStorage.setItem("token",response.rest.Authoritization)
        window.sessionStorage.setItem("type",response.rest.type)
     }
      return {RegisterCode,RegisterMessage,RegisterRest}
    }).catch(error=>{
    //发生错误时执行的代码
    console.log('异常响应')
    console.log(error)
    })
   
    return {RegisterCode,RegisterMessage,RegisterRest}
}
export async function registersUsernameExit(username){
    await usernameExit(username).then((response)=>{
      RegisterCode=response.code
      RegisterMessage=response.message
      RegisterRest=response.rest
        return {RegisterCode,RegisterMessage,RegisterRest}
      }).catch(error=>{
      //发生错误时执行的代码
      console.log('异常响应')
      console.log(error)
      })
      return {RegisterCode,RegisterMessage,RegisterRest}
  }
export {RegisterCode,RegisterMessage,RegisterRest}