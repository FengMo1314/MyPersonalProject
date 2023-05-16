import { getAllProductType, addProductType, updateProductType,getProductTypeByName } from '../api/ShopTypeApi'
let ShopTypeCode;
let ShopTypeRest;
let ShopTypeMessage;
export async function getAll() {
  await  getAllProductType().then((response) => {
       ShopTypeCode=response.code
       ShopTypeMessage=response.message
       ShopTypeRest=response.rest.allproducttype
       return
    }).catch(error => {
        //发生错误时执行的代码
        console.log('异常响应')
        console.log(error)
    })
    return
}
export async function getByName(name) {
    await  getProductTypeByName(name).then((response) => {
         ShopTypeCode=response.code
         ShopTypeMessage=response.message
         ShopTypeRest=response.rest.producttype
         return
      }).catch(error => {
          //发生错误时执行的代码
          console.log('异常响应')
          console.log(error)
      })
      return
  }
export async function addType(mesg) {
  await  addProductType(mesg).then((response) => {
       ShopTypeCode=response.code
       ShopTypeMessage=response.message
       ShopTypeRest=response.rest.allproducttype
    }).catch(error => {
        //发生错误时执行的代码
        console.log('异常响应')
        console.log(error)
    })
}
export async function updateType(mesg) {
  await  updateProductType(mesg).then((response) => {
       ShopTypeCode=response.code
       ShopTypeMessage=response.message
       ShopTypeRest=response.rest.allproducttype
    }).catch(error => {
        //发生错误时执行的代码
        console.log('异常响应')
        console.log(error)
    })
}
export { ShopTypeCode,ShopTypeMessage,ShopTypeRest }