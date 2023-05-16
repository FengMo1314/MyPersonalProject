<template>
  <el-upload   ref="upload" class="upload-demo" action="#" list-type="picture-card" :limit="1"
    :on-exceed="handleExceed" :auto-upload="false" :http-request="uploadAction">
    <el-icon>
      <Plus />
    </el-icon>
    <template #file="{ file,files }">
        <div>
          <img class="el-upload-list__item-thumbnail" :src="file.url" alt="" />
          <span class="el-upload-list__item-actions">
            <span
              class="el-upload-list__item-preview"
              @click="handlePictureCardPreview(file)"
            >
              <el-icon><zoom-in /></el-icon>
            </span>
            <span
              v-if="!disabled"
              class="el-upload-list__item-delete"
              @click="handleDownload(file)"
            >
              <el-icon><Download /></el-icon>
            </span>
            <span
              v-if="!disabled"
              class="el-upload-list__item-delete"
              @click="handleRemove(file,files)"
            >
              <el-icon><Delete /></el-icon>
            </span>
          </span>
        </div>
      </template>
  </el-upload>
  <el-button class="ml-3" type="success" @click="submitUpload">
    上传
  </el-button>
  <el-dialog v-model="dialogVisible">
    <img w-full :src="dialogImageUrl" alt="Preview Image" />
  </el-dialog>
</template>
<script lang="ts" setup>
import { ref, reactive, onMounted,toRefs } from 'vue'
import { Delete, Download, Plus, ZoomIn } from '@element-plus/icons-vue'
import { genFileId } from 'element-plus'
import { URL } from '../../utils/doFilesUrl'
import type { UploadFile, UploadInstance, UploadProps, UploadRawFile, UploadUserFile } from 'element-plus'
import { UploadFiles, SYSUploadFileCode, SYSUploadFileMessage, SYSUploadFileRest } from '../../JavaScript/SYSUploadFile'
const state = reactive({
  sendToFather:{},
  filesMesg: [],
})
onMounted(async () => {
  // await setFatherDate()
  // clickChild()
  doFilesList(imgUrl.value,state.filesMesg)
})
const props = defineProps({
  //子组件接收父组件传递过来的值
  isAdd: {
    type: Boolean,
    default: true
  },
  isInster: {
    type: Boolean,
    default: true
  },
  imgUrl: {
    type: String,
    default: ''
  }
})

//使用父组件传递过来的值-保持响应
const { imgUrl } = toRefs(props)
// 响应式-绑定父组件传来的数据
// const setFatherDate = async () => {
//   alert(JSON.stringify("父组件传来了"+JSON.stringify(props)))
//   console.log("响应式-绑定父组件传来的数据props",props);
//   dialogImageUrl.value = imgUrl
//   doFilesList(dialogImageUrl.value, state.filesMesg)
//   console.log('dialogImageUrl.value', dialogImageUrl.value);

// }
//   移除
const handleRemove: UploadProps['onRemove'] = (file: UploadFile) => {
  console.log(file)
}
//   放大
const handlePictureCardPreview = (file: UploadFile) => {
  dialogImageUrl.value = file.url!
  dialogVisible.value = true
}
//   下载
const handleDownload = (file: UploadFile) => {
  console.log(file)
}
//   覆盖图片
const upload = ref<UploadInstance>()
const handleExceed: UploadProps['onExceed'] = (files) => {
  upload.value!.clearFiles()
  const file = files[0] as UploadRawFile
  file.uid = genFileId()
  upload.value!.handleStart(file)
}
// 上传请求
const uploadAction = async (option: any) => {
  let param = new FormData();
  param.append('file', option.file);
  await UploadFiles(param)
  if (SYSUploadFileCode === 0) {
    state.sendToFather=SYSUploadFileRest.rest
    alert("上传文件成功")
    console.log("上传OK-imgurl：SYSUploadFileRest.rest.newFilePath", SYSUploadFileRest.rest.newFilePath);
    clickChild()
  }else if(SYSUploadFileCode === 1){
    alert(SYSUploadFileMessage)
  } else {
    alert("上传失败")
  }
  dialogImageUrl.value=''
}
//手动上传
const submitUpload = () => {
  upload.value!.submit()
}

const dialogImageUrl = ref(imgUrl.value)
const dialogVisible = ref(false)
// 使用defineEmits创建名称，接受一个数组,给父组件传值
const emit = defineEmits(['clickChild'])
const clickChild = () => {
  let param =state.sendToFather
  //传递给父组件
  emit('clickChild', param)
}
// 照片墙
const fileList = ref<UploadUserFile[]>(state.filesMesg)
const doFilesList = (list:string, rest: UploadUserFile[]) => {
  if (list===null||list==='') {
    return
  }
  let name = list
  let url = URL(list)
  let id = 1;
  rest.push({ "name": name, "url": url, "uid": id })
}
const disabled = ref(false)
</script>
  