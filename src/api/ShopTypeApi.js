import http from "../utils/http";
export function getAllProductType(){
    return http.get('/api/sys_product_type')
}
export function getProductTypeByName(name){
    return http.get('/api/sys_product_type/'+name)
}
export function addProductType(mesg){
    return http.post('/api/sys_product_type',mesg)
}
export function updateProductType(mesg){
    return http.put('/api/sys_product_type',mesg)
}