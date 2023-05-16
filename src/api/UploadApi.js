import http from '../utils/http'
export function upload(file){
  return  http.post('/api/file',file,'1')
} 
export function getSomeFilesByUid(){
  return http.get('/api/file')
}
export function deleteFile(filename){
  return http.delete('/api/file/'+filename)
}