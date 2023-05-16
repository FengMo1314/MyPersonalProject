import http from '../utils/http'

export function getAllUsers(){
  return http.get('/api/user')
}
export function getUsersByUId(){
  return http.get('/api/user/id')
}
export function getSomeUsers(currentPage,pageSize){
  return http.get('/api/user/'+currentPage+'/'+pageSize)
}
export function getUserByTypeName(usertype){
  return http.get('/api/user/'+usertype)
}
export function getUserByName(username){
  return http.get('/api/user/name/'+username)
}
export function addUser(user){
  return http.post('/api/user',user)
}
export function deleteUserById(uid){
  return http.delete('/api/user/'+uid)
}
export function deleteUserByIds(ids){
  return http.delete('/api/user/ids/'+ids)
}
export function updataUserById(user){
  return http.put('/api/user',user)
}
export function fishId(){
  return http.get("/api/user/fish")
}

