import {getAllProduct,addProduct,updateProduct,deleteProductByProductId} from '../api/ShopApi'
let ShopCode;
let ShopRest;
let ShopMessage;
export async function getAllShop() {
  await  getAllProduct().then((response) => {
       ShopCode=response.code
       ShopMessage=response.message
       ShopRest=response.rest.allproduct
       return
    }).catch(error => {
        //发生错误时执行的代码
        console.log('异常响应')
        console.log(error)
    })
    return
}
export async function addProductIng(product) {
    console.log('add+++++\t',product);
    await  addProduct(product).then((response) => {
        ShopCode=response.code
        ShopMessage=response.message
        return
     }).catch(error => {
         //发生错误时执行的代码
         console.log('异常响应')
         console.log(error)
     })
     return
  }
export async function updateProductIng(product) {
    await updateProduct(product).then((response) => {
        ShopCode=response.code
        ShopMessage=response.message
        return
     }).catch(error => {
         //发生错误时执行的代码
         console.log('异常响应')
         console.log(error)
     })
     return
}
export async function deleteProduct(productId) {
    await deleteProductByProductId(productId).then((response) => {
        ShopCode=response.code
        ShopMessage=response.message
        return
     }).catch(error => {
         //发生错误时执行的代码
         console.log('异常响应')
         console.log(error)
     })
     return
}
export { ShopCode,ShopMessage,ShopRest }