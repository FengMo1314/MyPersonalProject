export function addForever(key,value){
  return window.localStorage.setItem(key,value)
}
export function getForever(key){
  return window.localStorage.getItem(key)
}