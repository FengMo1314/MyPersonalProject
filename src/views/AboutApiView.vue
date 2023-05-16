<template>
  <div class="about">
    <h1>API接口说明</h1>
    <div>
      <h2>测试数据反馈</h2>
      <el-input
    v-model="data.api"
    maxlength="30"
    placeholder="Please input"
    show-word-limit
    type="textarea"
   
    
    readonly="true"
  />
      <!-- <textarea name="" id="api" cols="30" rows="10" v-model="data.api" readonly/> -->
    </div>
    <ul>
      <li>
        接口1:
        <!-- <a href="http://localhost:8088/lh.com/listings/allListings/1/20"> -->
        http://localhost:8088/lh.com/listings/allListings/currentPage/pageSize
        <!-- </a> -->
        <ul>
          <li>参数:</li>
          <ul>
            <li>请求方式：POST</li>
            <li>currentPage:分页起始页</li>
            <li>pageSize:每页显示条数</li>
            <li>传入参数：{
              "mesg":mesg
              }
              非条件模糊查询:mesg=""或者null或者"null"
              模糊查询:mesg=String
            </li>
          </ul>
          试一试:
          <ul>
            <li>
              currentPage:<input type="number" min="1" placeholder="指定当前页数" name="" id="" v-model="data.currentPage">
            </li>
            <li>
              pageSize:<input type="number" min="1" max="150" placeholder="指定每页显示的数据数" name="" id="" v-model="data.pageSize">
            </li>
            <li>
              mesg:<input type="text" placeholder="模糊查询的条件" name="" id="" v-model="data.mesg">
            </li>
          </ul>
          
          <button @click="chick">提交</button>
        </ul>
      </li>
    </ul>
  </div>
</template>
<script setup>
import { onMounted, reactive, ref } from 'vue'
import { getAllListings } from '../api/ListingsApi';
const data=reactive({
    currentPage:'',
    pageSize:'',
    mesg:'',
    api:json(["默认显示",{"mesg":"默认显示"}])
  })
  function json(mesg){
    return JSON.stringify(mesg,null,4)
  }
async function chick(){
  console.log(data.currentPage,data.pageSize,data.mesg);
 await getAllListings(data.currentPage,data.pageSize,data.mesg).then((response) => {
   data.api=json(response.rest.allListings.list)
    console.log('正常响应:',response.rest.allListings.list)
  }).catch(error => {
    //发生错误时执行的代码
    data.api=json(error.message)
    console.log('异常响应')
    console.log(error)
  })
}
</script>

<style>


</style>
