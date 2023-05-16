// 获取后端数据
import { getAllListings } from '../api/ListingsApi';
let ListingsCode;
let ListingsMessage;
let ListingsRest;
export async function allListingsByPage(pageNum, pageSize, mesg) {
  await getAllListings(pageNum, pageSize, mesg).then((response) => {
    ListingsCode = response.code
    ListingsMessage = response.message
    ListingsRest = response.rest.allListings
    return
  }).catch(error => {
    //发生错误时执行的代码
    console.log('异常响应')
    console.log(error)
  })
  return { ListingsCode, ListingsMessage, ListingsRest }
}
export { ListingsCode, ListingsMessage, ListingsRest }
