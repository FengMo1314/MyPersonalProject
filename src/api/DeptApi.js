import http from "../utils/http";
export function getAllDept(){
    return http.get('/api/sys_dept')
}
export function addDept(dept){
   return http.post('/api/sys_dept',dept)
}
export function disable(mesg){
    return http.put('/api/sys_dept/disable/'+mesg)
}
export function enable(mesg){
    return http.put('/api/sys_dept/enable/'+mesg)
}
export function updateDeptById(dept){
    return http.put('/api/sys_dept',dept)
 }