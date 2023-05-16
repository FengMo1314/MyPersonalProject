import { login } from '../api/LoginApi'
import { loginUser } from '../api/SYSLoginApi';
let code;
let rests;
let token;
export async function isLogin(ruleForm) {
    //
    await login(ruleForm).then((response) => {
        console.log('正常响应')
        console.log(response)
        code = response.code
        rests = response.rest
        token = response.rest.Authoritization
        if (code === 0) {
            // 存入token
            window.sessionStorage.setItem("token", token)
            window.sessionStorage.setItem("type", rests.type)
                if (rests.imgUrl) {
                    console.log("写入imgUrl：", rests.imgUrl);
                    window.sessionStorage.setItem('imgUrl', rests.imgUrl)
                }
        }
        return { code, rests, token }
    }).catch(error => {
        //发生错误时执行的代码
        console.log('异常响应')
        console.log(error)
    })
    return { code, rests, token }
}
export async function isSYSLoginUser(ruleForm) {
    //
    await loginUser(ruleForm).then((response) => {
        console.log('正常响应')
        console.log(response)
        code = response.code
        rests = response.rest
        token = response.rest.Authoritization
        if (code === 0) {
            // 存入token
            window.sessionStorage.setItem("token", token)
            window.sessionStorage.setItem("type", rests.type)
                if (rests.imgUrl) {
                    console.log("写入imgUrl：", rests.imgUrl);
                    window.sessionStorage.setItem('imgUrl', rests.imgUrl)
                }
        }
        return { code, rests, token }
    }).catch(error => {
        //发生错误时执行的代码
        console.log('异常响应')
        console.log(error)
    })
    return { code, rests, token }
}
// export function isCoid(state, mesg) {
//     checkCoid(mesg).then((response) => {
//         state.is = response.message
//         console.log("function isCoid：", response.message);
//     }).catch(error => {
//         //发生错误时执行的代码
//         console.log('异常响应')
//         console.log(error)
//     })
// }
