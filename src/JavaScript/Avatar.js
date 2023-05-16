import { getAvatar } from '../api/AvatarApi'
let AvatarCOde
let AvatarMessage;
let AvatarRest;
export async function getAvatarImgUrl(){
    await getAvatar().then((response) => {
        AvatarCOde=response.code
        AvatarRest= response.rest.imgUrl
        console.log("AvatarRest", AvatarRest);
        return AvatarRest
    }).catch(error => {
        //发生错误时执行的代码
        console.log('allCarousels异常响应')
        console.log(error)
    })
} 
export { AvatarCOde,AvatarMessage,AvatarRest}