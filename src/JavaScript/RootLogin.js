import { loginRoot } from '../api/SYSLoginApi'
let RootLoginCode;
let RootLoginRest;
let token;
let RootLoginMessage;
export async function isLogin(ruleForm) {
    await loginRoot(ruleForm).then((response) => {
     RootLoginCode = response.code
     RootLoginMessage=response.message
     RootLoginRest= response.rest
        token = response.rest.Authoritization
        if (RootLoginCode === 0) {
            // 存入token
            window.sessionStorage.setItem("token", token)
            window.sessionStorage.setItem("type",RootLoginRest.type)
                if (rests.imgUrl) {
                    console.log("写入imgUrl：", RootLoginRest.imgUrl);
                    window.sessionStorage.setItem('imgUrl', RootLoginRest.imgUrl)
                }
        }
        return
    }).catch(error => {
        //发生错误时执行的代码
        console.log('异常响应')
        console.log(error)
    })
    return
}
export {RootLoginCode,RootLoginMessage,RootLoginRest}