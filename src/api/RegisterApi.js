import http from '../utils/http'

export function register(mesg){
  return http.post('/api/register',mesg)
}
export function usernameExit(username){
    return http.get('/api/register/'+username)
}