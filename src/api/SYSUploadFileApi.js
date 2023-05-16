import http from '../utils/http'
export function upload(file){
  return  http.post('/api/sys_file',file,1)
} 