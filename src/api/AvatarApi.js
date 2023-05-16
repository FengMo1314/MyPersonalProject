import http from '../utils/http'
export function getAvatar(){
    return http.get('/api/avatar')
}