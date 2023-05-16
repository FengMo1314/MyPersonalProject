import { ref } from "vue";

const pageSize=ref<number>();
const pageNum=ref<number>();
//每页显示条数
export const handleSizeChange = async(val: number) => {
  console.log(`${val} items per page`)
  pageSize.value=val
}
// 当前选择的页数
export const handleCurrentChange = async(val: number) => {
  console.log(`current page: ${val}`)
  pageNum.value=val
}
export function pager(state){
  state.pageNum=pageNum.value;
  state.pageSize=pageSize.value;
  console.log("RRRRRR",pageNum,"ttttt",pageSize);
}
export{pageSize,pageNum}