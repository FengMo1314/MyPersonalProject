import { getAllDept,addDept,disable,enable,updateDeptById } from '../api/DeptApi';
let DeptCode;
let DeptMessage;
let DeptRest;
export async function allDept(){
    await getAllDept().then((response)=>{
       DeptCode=response.code 
       DeptMessage=response.message
       DeptRest=response.rest.alldept
       return
    }).catch(error => {
        //发生错误时执行的代码
        console.log('allDept异常响应')
        console.log(error)
    })
    return 
}
export async function addDeptIng(dept){
    await addDept(dept).then((response)=>{
       DeptCode=response.code 
       DeptMessage=response.message
       return
    }).catch(error => {
        //发生错误时执行的代码
        console.log('allCarousels异常响应')
        console.log(error)
    })
    return 
}
export async function disableIng(mesg){
    await disable(mesg).then((response)=>{
       DeptCode=response.code 
       DeptMessage=response.message
       return
    }).catch(error => {
        //发生错误时执行的代码
        console.log('allCarousels异常响应')
        console.log(error)
    })
    return 
}
export async function enableIng(mesg){
    await enable(mesg).then((response)=>{
       DeptCode=response.code 
       DeptMessage=response.message
       return
    }).catch(error => {
        //发生错误时执行的代码
        console.log('allCarousels异常响应')
        console.log(error)
    })
    return 
}
export async function updateDept(dept){
    await updateDeptById(dept).then((response)=>{
       DeptCode=response.code 
       DeptMessage=response.message
       DeptRest=response
       return
    }).catch(error => {
        //发生错误时执行的代码
        console.log('upDept异常响应')
        console.log(error)

    })
    return 
}
export {DeptCode,DeptMessage,DeptRest}
