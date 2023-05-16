import http from '../utils/http'
export function getAllProduct(){
    return http.get('/api/sys_product')
}
export function addProduct(sysProduct){
    return http.post('/api/sys_product',sysProduct)
}
export function updateProduct(sysProduct){
    return http.put('/api/sys_product',sysProduct)
}
export function deleteProductByProductId(id){
    return http.delete('/api/sys_product/'+id)
}