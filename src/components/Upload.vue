<template>
  <!--  -->
  <h2>主头像</h2>
  <hr class="my-4" />
  <div class="demo-type">
    <div v-if="state.url === '' || state.url === null">
      <el-avatar :icon="UserFilled" size="large" />
    </div>
    <!-- <div>
      <el-avatar> user </el-avatar>
    </div> -->
    <div v-else>
      <el-avatar :src="state.url" />
    </div>
  </div>
  <hr class="my-4" />
  <!-- 头像-1 -->
  <h3>头像预览区</h3>
  <el-scrollbar>
    <div class="demo-fit">
      <div v-for="fit in fits" :key="fit" class="block">
        <span class="title">{{ fit }}</span>
        <el-avatar shape="square" :size="100" :fit="fit" :src="url" />
      </div>
    </div>
  </el-scrollbar>
  <hr class="my-4" />
  <!-- 头像-2 -->
  <h3>照片墙</h3>
  <hr class="my-4" />
  <div>
    <el-upload 
    v-model:file-list="fileList" 
    :http-request="uploadAction" 
    list-type="picture-card"
    :on-preview="handlePictureCardPreview" 
    :on-remove="handleRemove">
      <el-icon>
        <Plus />
      </el-icon>
    </el-upload>
    <hr class="my-4" />
    <el-dialog v-model="dialogVisible">
      <el-image w-full :src="dialogImageUrl" alt="Preview Image" />
    </el-dialog>
  </div>
  <hr class="my-4" />
</template>
<script lang="ts" setup>
import { ref, onMounted, reactive, toRefs } from 'vue'
import { Delete, Download, Plus, ZoomIn } from '@element-plus/icons-vue'
import { UploadProps, UploadUserFile, UploadFile, alertEffects } from 'element-plus'
import { UploadFiles, getUsersFiles, deleteUsersFiles, UploadCode, UploadMessage, UploadRest } from '../JavaScript/Upload';
import { getAvatarImgUrl, AvatarCOde, AvatarRest, AvatarMessage } from '../JavaScript/Avatar'
import { URL } from '../utils/doFilesUrl'
import { UserFilled } from '@element-plus/icons-vue'
onMounted(async () => {
  await getUsersFiles()
  state.getFiles = UploadRest
  doFilesList(state.getFiles, state.filesMesg)
  getAva()
})
const imgUrl = window.sessionStorage.getItem('imgUrl')
const dialogImageUrl = ref('')
const dialogVisible = ref(false)
const disabled = ref(false)
const state = reactive({
  getFiles: [],
  filesMesg: [],
  isUpsuss: true,
  fits: ['fill', 'contain', 'cover', 'none', 'scale-down'],
  url: ''
})
interface file {
  id: number,
  file_name: string,
  file_path: string,
  uid: number
}
const getAva = async () => {
  if (imgUrl === '' || imgUrl === null) {
    await getAvatarImgUrl()
    if (AvatarCOde === 0) {
      state.url = AvatarRest
      return
    } else {
      state.url = null
    }
  } else {
    state.url = imgUrl
    return
  }
}
const doFilesList = (list: file[], rest: UploadUserFile[]) => {
  list.forEach(items => {
    let name = items.file_name
    let url = URL(items.file_path)
    let id = items.id;
    rest.push({ "name": name, "url": url, "uid": id })
  });
}
// 
const fileList = ref<UploadUserFile[]>(state.filesMesg)
// 移除上传
const handleRemove: UploadProps['onRemove'] = async (uploadFile, uploadFiles) => {
  let fileId = uploadFile.uid
  await deleteUsersFiles(fileId)
  if (UploadCode != 0) {
    uploadFiles.push(uploadFile)
    console.log('点了删除但是失败了', uploadFile, uploadFiles)
  }
  alert(UploadMessage)
}
// 大图浏览
const handlePictureCardPreview: UploadProps['onPreview'] = (uploadFile) => {
  console.log("dianle", handlePictureCardPreview);

  dialogImageUrl.value = uploadFile.url!
  dialogVisible.value = true
}
// 下载图
const handleDownload = (file: UploadFile) => {
  console.log(file)
}
// 上传请求
const uploadAction = async (option: any) => {
  let param = new FormData();
  param.append('file', option.file);
  await UploadFiles(param)
  if (UploadCode === 0) {
    alert("上传OK")
  } else {
    alert(UploadMessage)
    await getUsersFiles()
    state.getFiles = UploadRest
    doFilesList(state.getFiles, state.filesMesg)
  }
}
// 头像
const { fits, url } = toRefs(state)
</script>
<style>
h1,
h2,
h3,
h4,
h5 {
  font-size: xx-large;
  font-weight: lighter;
  font-family: 'Trebuchet MS', 'Lucida Sans Unicode', 'Lucida Grande', 'Lucida Sans', Arial, sans-serif;
  text-align: center;
}
.el-dialog{
  width: 50%;
  height: 50%;
}
.el-dialog .el-image{
  width: 100%;
  height: 100%;
}

.el-upload-list--picture-card {
  display: table-row-group
}

.demo-type {
  width: 100%;
  position: relative;
}

.demo-type div {
  width: 200px;
  height: 200px;
  margin: 0 auto;
}

.demo-type div .el-avatar {
  width: 100%;
  height: 100%;
}

.demo-fit {
  margin: 0 auto;
  display: flex;
  text-align: center;
  justify-content: space-between;
}

.demo-fit .block {
  flex: 1;
  display: flex;
  flex-direction: column;
  flex-grow: 0;
}</style>
  