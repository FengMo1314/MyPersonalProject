import { upload,getSomeFilesByUid,deleteFile } from "../api/UploadApi";
let UploadCode=10;
let UploadMessage;
let UploadRest;
export async function UploadFiles(file){
    await upload(file).then((response)=>{
      UploadCode=response.code
      UploadMessage=response.message
      UploadRest=response.rest

    }).catch(error=>{
       //发生错误时执行的代码
    console.log('异常响应')
    UploadCode=error.response.data.code
    UploadMessage=error.response.data.message
    console.log(error)
    })
    return {UploadCode,UploadMessage,UploadRest}
}
export async function getUsersFiles(){
  await getSomeFilesByUid().then((response)=>{
    UploadCode=response.code
    UploadMessage=response.message
    UploadRest=response.rest.somefiles

  }).catch(error=>{
     //发生错误时执行的代码
  console.log('异常响应')
  console.log(error)
  })
  return {UploadCode,UploadMessage,UploadRest}
}
export async function deleteUsersFiles(fileId){
  await deleteFile(fileId).then((response)=>{
    UploadCode=response.code
    UploadMessage=response.message
    UploadRest=response.rest

  }).catch(error=>{
     //发生错误时执行的代码
  console.log('deleteUsersFiles异常响应')
  console.log(error)
  })
  return {UploadCode,UploadMessage,UploadRest}
}
export {UploadCode,UploadMessage,UploadRest}