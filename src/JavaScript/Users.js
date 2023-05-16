import { getAllUsers,getSomeUsers,getUsersByUId,getUserByTypeName,getUserByName, addUser,deleteUserById,deleteUserByIds,updataUserById, fishId} from '../api/UsersApi'
let UsersCode;
let UsersMessage;
let UsersRest;
export async function allU() {
   await getAllUsers().then((response) => {
        UsersCode=response.code
        UsersMessage=response.message
        UsersRest=response.rest.allUsers
        return
    }).catch(error => {
        //发生错误时执行的代码
        console.log('getAllUsers异常响应')
        console.log(error)
    })
    return {UsersCode,UsersMessage,UsersRest}
}
export async function getUByPage(currentPage,pageSize){
    await getSomeUsers(currentPage,pageSize).then((response)=>{
        UsersCode=response.code
        UsersMessage=response.message
        UsersRest=response.rest.somUsers
        return
     }).catch(error=>{
         //发生错误时执行的代码
         console.log('getUByPage异常响应')
         console.log(error)
     })
     return {UsersCode,UsersMessage,UsersRest}
 }
export async function getUByName(name){
   await getUserByTypeName(name).then((response)=>{
    UsersCode=response.code
    UsersMessage=response.message
    UsersRest=response.rest.restUsers
    return
    }).catch(error=>{
        //发生错误时执行的代码
        console.log('addUser异常响应')
        console.log(error)
    })
    return {UsersCode,UsersMessage,UsersRest}
}
export async function getUHasName(name){
    await getUserByName(name).then((response)=>{
     UsersCode=response.code
     UsersMessage=response.message
     UsersRest=response.rest
     return
     }).catch(error=>{
         //发生错误时执行的代码
         console.log('addUser异常响应')
         console.log(error)
     })
     return {UsersCode,UsersMessage,UsersRest}
 }
export async function addU(state){
   await addUser(state).then((response)=>{
    UsersCode=response.code
    UsersMessage=response.message
    UsersRest=response.rest
    return
    }).catch(error=>{
        //发生错误时执行的代码
        console.log('addUser异常响应')
        console.log(error)
    })
    return {UsersCode,UsersMessage,UsersRest}
}
export async function getUById(){
    await getUsersByUId().then((response)=>{
     UsersCode=response.code
     UsersMessage=response.message
     UsersRest=response.rest.restUsers
     return
     }).catch(error=>{
         //发生错误时执行的代码
         console.log('addUser异常响应')
         console.log(error)
     })
     return {UsersCode,UsersMessage,UsersRest}
 }
export async function deleteU(uId){
   await deleteUserById(uId).then((response)=>{
    UsersCode=response.code
    UsersMessage=response.message
    UsersRest=response.rest
    return
    }).catch(error=>{
        //发生错误时执行的代码
        console.log('deleteUserById异常响应')
        console.log(error)
    })
    return {UsersCode,UsersMessage,UsersRest}
}
export async function updatedU(users) {
   await updataUserById(users).then((response)=>{
    UsersCode=response.code
    UsersMessage=response.message
    UsersRest=response.rest
    return
    }).catch(error=>{
         //发生错误时执行的代码
         console.log('updataUserById异常响应')
         console.log(error)
    })
    return {UsersCode,UsersMessage,UsersRest}
}
// 通过多选ids删除用户
export async function deleteUIds(ids){
    await deleteUserByIds(ids).then((response)=>{
        UsersCode=response.code
        UsersMessage=response.message
        UsersRest=response.rest
        return
     }).catch(error=>{
         //发生错误时执行的代码
         console.log('deleteUserByIds异常响应')
         console.log(error)
     })
     return {UsersCode,UsersMessage,UsersRest}
 }
 export async function fish(){
    await fishId().then((response)=>{
        UsersCode=response.code
        UsersMessage=response.message
        UsersRest=response.rest
        return
    }).catch(error=>{
        //发生错误时执行的代码
        console.log('fishId异常响应')
        console.log(error)
    })
    return {UsersCode,UsersMessage,UsersRest}
 }
export {UsersCode,UsersMessage,UsersRest}