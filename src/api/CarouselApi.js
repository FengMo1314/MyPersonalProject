
import http from '../utils/http'

export function getAllCarousels(){
  return  http.get('/api/carousels')
}
export function getCarouselsByUrl(url){
  return http.get('/api/carousels/'+url)
}
export function addCarousels(carousels){
  return http.get('/api/carousels',carousels)
}
export function deleteCarouselsById(id){
  return http.delete('/api/carousels/'+id)
}
export function updataCarouselsById(carousels){
  return http.put('/api/carousels',carousels)
}
export function deleteCarouselsByIds(ids){
  return http.delete('/api/carousels/ids/'+ids)
}