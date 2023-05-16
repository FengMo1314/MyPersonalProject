<template>
    <el-switch @click="clickChild" v-if="state.largeActiveVal !== ''" v-model="Lvalue" size="large" :active-text="state.largeActiveVal"
        :inactive-text="state.largeInactiveVal" />
    <el-switch @click="clickChild" v-if="state.centerActiveVal !== ''" v-model="Cvalue" :active-text="state.centerActiveVal"
        :inactive-text="state.centerInactiveVal" />
    <el-switch @click="clickChild" v-if="state.smallActiveVal !== ''" v-model="Svalue" size="small" :active-text="state.smallActiveVal"
        :inactive-text="state.smallInactiveVal" />
</template>

<script lang="ts" setup>
import { ref, reactive, toRefs, onMounted } from 'vue'

const state = reactive({
    largeActiveVal: '',
    largeInactiveVal: '',
    centerActiveVal: '',
    centerInactiveVal: '',
    smallInactiveVal: '',
    smallActiveVal: '',
})
const props = defineProps({
    //子组件接收父组件传递过来的值

    Pstate: {
        type: Object,
        default: {
            LA: '',
            LI: '',
            CA: '',
            CI: '',
            SA: '',
            SI: '',
        }
    }
})
onMounted(() => {
    setVal()
})
const setVal = () => {
    console.log(Pstate.value);
    if(Pstate.value.LA){
    state.largeActiveVal = Pstate.value.LA;
    }
    if(Pstate.value.LI){
    state.largeInactiveVal = Pstate.value.LI
    }
    if(Pstate.value.CA){ 
    state.centerActiveVal = Pstate.value.CA
    }
    if(Pstate.value.CI){
    state.centerInactiveVal = Pstate.value.CI
    }
    if(Pstate.value.SA){
    state.smallActiveVal = Pstate.value.SA
    }
    if(Pstate.value.SI){
    state.smallInactiveVal = Pstate.value.SI
    }
}
//使用父组件传递过来的值
const { Pstate } = toRefs(props)

const Lvalue = ref(false)
const Cvalue = ref(false)
const Svalue = ref(false)
// 使用defineEmits创建名称，接受一个数组
const emit = defineEmits(['clickChild'])
const clickChild=()=>{
  let param={
    LV:Lvalue.value,
    CV:Cvalue.value,
    SV:Svalue.value
  }
  //传递给父组件
  emit('clickChild',param)
}
</script>
<style>
.el-switch {
    margin: 5px 20px;
}
</style>
  