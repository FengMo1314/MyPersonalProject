import http from '../utils/http'

export function getAllListings(page,size,mesg){
  return http.post('/api/listings/allListings/'+page+'/'+size,{"mesg":mesg})
}