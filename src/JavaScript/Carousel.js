// 获取后端数据
import { getAllCarousels, getCarouselsByUrl, addCarousels, deleteCarouselsById, updataCarouselsById, deleteCarouselsByIds } from '../api/CarouselApi';
let CarouselsCode;
let CarouselsMessage;
let CarouselsRest;
export  async function allCarousels() {
    await getAllCarousels().then((response) => {
        CarouselsRest = response.rest.allCarouses
        console.log("CarouselsRest", CarouselsRest);
        return CarouselsRest
    }).catch(error => {
        //发生错误时执行的代码
        console.log('allCarousels异常响应')
        console.log(error)
    })
    return CarouselsRest
}
export async function getOneCarouselsByUrl(url) {
    await getCarouselsByUrl(url).then((response) => {
        CarouselsRest = response.rest.someCarouses
        CarouselsMessage = response.message
        return { CarouselsRest, CarouselsMessage }
    }).catch(error => {
        //发生错误时执行的代码
        console.log('oneCarouselsByUrl异常响应')
        console.log(error)
    })
    return { CarouselsRest, CarouselsMessage }
}
export async function addOneCarousels(carousels) {
    await addCarousels(carousels).then((response) => {
        CarouselsCode = response.code
        CarouselsMessage = response.message
        return { CarouselsCode, CarouselsMessage }
    }).catch(error => {
        //发生错误时执行的代码
        console.log('addCarousels异常响应')
        console.log(error)
    })
    return { CarouselsCode, CarouselsMessage }
}
export async function deleteOneCarouselsById(id) {
    await deleteCarouselsById(id).then((response) => {
        CarouselsCode = response.code
        CarouselsMessage = response.message
        return { CarouselsCode, CarouselsMessage }
    }).catch(error => {
        //发生错误时执行的代码
        console.log('addCarousels异常响应')
        console.log(error)
    })
    return { CarouselsCode, CarouselsMessage }
}
export async function deleteSomeCarouselsByIds(ids) {
    await deleteCarouselsByIds(ids).then((response) => {
        CarouselsCode = response.code
        CarouselsMessage = response.message
        return { CarouselsCode, CarouselsMessage }
    }).catch(error => {
        //发生错误时执行的代码
        console.log('addCarousels异常响应')
        console.log(error)
    })
    return { CarouselsCode, CarouselsMessage }
}
export async function updateOneCarouselsById(data) {
    await updataCarouselsById(data).then((response) => {
        CarouselsCode = response.code
        CarouselsMessage = response.message
        return { CarouselsCode, CarouselsMessage }
    }).catch(error => {
        //发生错误时执行的代码
        console.log('addCarousels异常响应')
        console.log(error)
    })
    return { CarouselsCode, CarouselsMessage }
}
export { CarouselsCode, CarouselsMessage, CarouselsRest }