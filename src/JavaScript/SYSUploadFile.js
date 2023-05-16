import { upload} from "../api/SYSUploadFileApi";
let SYSUploadFileCode=10;
let SYSUploadFileMessage;
let SYSUploadFileRest;
export async function UploadFiles(file){
    await upload(file).then((response)=>{
        SYSUploadFileCode=response.code
        SYSUploadFileMessage=response.message
        SYSUploadFileRest=response.rest
      return
    }).catch(error=>{
       //发生错误时执行的代码
    console.log('异常响应')
    SYSUploadFileCode=error.response.data.code
    SYSUploadFileMessage=error.response.data.message
    console.log(error)
    })
   return
}
export {SYSUploadFileCode,SYSUploadFileMessage,SYSUploadFileRest }