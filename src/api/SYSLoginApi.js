import http from '../utils/http'
export function loginUser(mesg){
    return http.post('/api/sys_login/user',mesg)
}
export function loginRoot(mesg){
    return http.post('/api/sys_login/root',mesg)
}