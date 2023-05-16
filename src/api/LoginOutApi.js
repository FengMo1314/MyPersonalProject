import http from '../utils/http'
export function loginOut(){
  return  http.get('/api/loginOut');
}