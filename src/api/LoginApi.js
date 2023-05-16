import http from '../utils/http'

export function login(mesg){
  return http.post('/api/login',{"username":mesg.username,"password":mesg.password})
}