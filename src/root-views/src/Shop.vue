<template>
    <div class="box">
        <el-scrollbar>
            <el-header style="text-align: center;">
                <h2>商品管理</h2>
            </el-header>
            <el-header>
                <el-button @click="dialogVisibleAdd = true">
                    添加商品
                </el-button>
                <h3>{{ error }}</h3>
            </el-header>

            <el-table :data="state.shopDate" style="width:100%;" height="400" v-loading="loading" :element-loading-svg="svg"
                :element-loading-text="loadingMesg" border>
                <el-table-column type="index" :index="indexMethod" label="序号"/>
                <el-table-column fixed prop="product_no" label="编号" />
                <el-table-column prop="name" label="商品" />
                <el-table-column prop="price" label="价格" />
                <el-table-column prop="productType.name" label="产品类型" />
                <el-table-column prop="productType.status" label="状态">
                    <template #default="scope">
                        <el-tag :type="scope.row.productType.status === 0 ? 'success' : 'danger'" disable-transitions>{{
                            scope.row.productType.status === 0 ? '有效商品' : '无效商品' }}</el-tag>
                    </template>
                </el-table-column>
                <el-table-column prop="product_image" label="图片">
                    <!-- <div class="demo-image__lazy">
                        <el-image  :src="URL(state.shop.product_image)" lazy />
                      </div> -->
                    <template #default="scope">
                        <el-popover :width="300"
                            popper-style="box-shadow: rgb(14 18 22 / 35%) 0px 10px 38px -10px, rgb(14 18 22 / 20%) 0px 10px 20px -15px; padding: 20px;">
                            <template #reference>
                                <el-image :src="URL(scope.row.product_image)"/>
                            </template>
                            <template #default>
                                <div class="demo-rich-conent" style="display: flex; gap: 16px; flex-direction: column">
                                    <el-image :src="URL(scope.row.product_image)" style="margin-bottom: 8px"
                                       />
                                </div>
                            </template>
                        </el-popover>
                    </template>
                </el-table-column>
                <el-table-column fixed="right" label="操作">
                    <template #default="scope">
                        <el-button link type="primary" size="small" @click="insterShow(scope.row)">修改</el-button>
                        <el-button link type="primary" size="small" @click="delectShop(scope.row.product_id)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </el-scrollbar>
    </div>
    <!-- 添加商品-1 -->
    <el-dialog v-model="dialogVisibleAdd" title="添加商品" width="40%" draggable>
        <h3>{{ error }}</h3>
        <el-form ref="ruleFormRef" :model="ruleForm" status-icon :rules="rules" label-width="120px" class="demo-ruleForm">
            <el-form-item label="商品名称" prop="name">
                <el-input v-model="ruleForm.name" />
            </el-form-item>
            <el-form-item label="商品价格" prop="price">
                <el-input v-model="ruleForm.price" />
            </el-form-item>
            <el-form-item label="商品图片" prop="product_image">
                <template #default="scope">
                    <Upload :isAdd="dialogVisibleAdd" @clickChild="clickEven" />
                </template>
            </el-form-item>
            <el-form-item label="商品类型" prop="productType.status">
                <el-select v-model="ruleForm.productType.name" placeholder="Select">
                    <el-option v-for="item in state.shopTypes" :key="item.id" :label="item.name" :value="item.name"
                        :disabled="item.status == 0 ? false : true" />
                </el-select>
            </el-form-item>
        </el-form>
        <template #footer>
            <span class="dialog-footer">
                <el-button @click="dialogVisible()">取消</el-button>
                <el-button type="primary" @click="submitAddDept()">
                    添加
                </el-button>
            </span>
        </template>
    </el-dialog>
    <!-- 修改商品 -->
    <el-dialog v-model="dialogVisibleInster" title="修改商品" width="40%" draggable>
        <h3>{{ error }}</h3>
        <el-form ref="ruleFormRef" :model="ruleForm" status-icon :rules="rules" label-width="120px" class="demo-ruleForm">
            <el-form-item label="编号" prop="product_no">
                <el-input v-model="ruleForm.product_no" disabled />
            </el-form-item>
            <el-form-item label="商品名称" prop="name">
                <el-input v-model="ruleForm.name" />
            </el-form-item>
            <el-form-item label="商品价格" prop="price">
                <el-input v-model="ruleForm.price" />
            </el-form-item>
            <el-form-item label="商品图片" prop="product_image">
                <Upload :isInster="dialogVisibleInster" @clickChild="clickEven" :imgUrl="ruleForm.product_image" />
            </el-form-item>
            <el-form-item label="商品类型" prop="productType.status">
                <el-select v-model="ruleForm.productType.name" :placeholder="ruleForm.productType.name">
                    <el-option v-for="item in state.shopTypes" :key="item.id" :label="item.name" :value="item.name"
                        :disabled="item.status == 0 ? false : true" />
                </el-select>
            </el-form-item>
        </el-form>
        <template #footer>
            <span class="dialog-footer">
                <el-button @click="dialogVisible()">取消</el-button>
                <el-button type="primary" @click="submitInster(ruleFormRef)">
                    修改
                </el-button>
                <el-button @click="resetForm(ruleFormRef)">重置</el-button>
            </span>
        </template>
    </el-dialog>
</template>
    
<script lang="ts" setup>
import Upload from './Upload.vue';
import { URL } from '../../utils/doFilesUrl'
import { getAll, ShopTypeCode, ShopTypeMessage, ShopTypeRest } from '../../JavaScript/ShopType'
import { ShopType } from './ShopType.vue'
import { getAllShop, addProductIng, updateProductIng, deleteProduct, ShopRest, ShopCode, ShopMessage } from '../../JavaScript/Shop'
import { reactive, ref, onMounted } from 'vue'
import type { FormInstance, FormMetaProps, FormRules } from 'element-plus'
// 全局数据组
const state = reactive({
    shop: <shop>{},
    shopDate: <shop[]>[],
    shopTypes: <ShopType[]>[],
    // newFilePath:
    //     "",
    // oldFileName:
    //     "",
    // urlPath:
    //     ""
})
// 注册一个回调函数，在组件挂载完成后执行。
onMounted(
    () => {
        all()
    })
// 定义类型
interface shop {
    product_id: number,
    product_no: string,
    name: string,
    price: number,
    product_image: string,
    product_type_id: number,
    productType: ShopType
}
// 获取后台全部库存
const all = async () => {
    loading.value = true
    await getAllShop()
    await getAll()
    state.shopTypes = ShopTypeRest
    state.shopDate = ShopRest
    console.log("加载Dept", state.shopDate);
    if (state.shopDate.length > 0 && state.shopTypes.length > 0) {
        loading.value = false
    }

}
// 部门添加框
const dialogVisibleAdd = ref(false)
// 修改部门
const dialogVisibleInster = ref(false)
// 消息提示
const error = ref('')
// 加载动画控制
const loading = ref(true)
// 加载文字控制
const loadingMesg = ref('Loadinf...')
// 自定义索引/序号
const indexMethod = (index: number) => {
  return index++
}
// 回显方法 
const show = async (Shop: shop) => {
    console.log(JSON.stringify("回显：" + JSON.stringify(Shop)))
    ruleForm.name = Shop.name
    ruleForm.price = Shop.price
    ruleForm.productType = Shop.productType
    ruleForm.product_id = Shop.product_id
    ruleForm.product_image = Shop.product_image
    ruleForm.product_no = Shop.product_no
    ruleForm.product_type_id = Shop.product_type_id
}
const dialogVisible = async () => {
    dialogVisibleAdd.value = false
    dialogVisibleInster.value = false
    state.shop = <shop>{}
    ruleForm.product_id = 0,
        ruleForm.name = '',
        ruleForm.price = 0,
        ruleForm.product_image = '',
        ruleForm.product_no = '',
        ruleForm.productType = <ShopType>{
            id: -1,
            name: '',
            status: -1
        }
}
// 修改回显
const insterShow = async (Shop: shop) => {
    await dialogVisible()
    await show(Shop)
    dialogVisibleInster.value = true;
}
const clickEven = (val: any) => {
    console.log(JSON.stringify("检查子组件传来的值" + JSON.stringify(val)))
    ruleForm.product_image = val.newFilePath
}
// 提交添加
const submitAddDept = async () => {
    await ShopTypeRest.forEach(val => {
        if (val.name === ruleForm.productType.name) {
            console.log('遍历得到val', val);
            ruleForm.product_type_id = val.id
        }
    });
    console.log('提交添加表单信息', ruleForm);
    await addProductIng(ruleForm)
    alert(ShopMessage)
    switch (ShopCode) {
        case 0:
            alert(ShopMessage)
            state.shop = <shop>{}
            dialogVisibleAdd.value = false
            await all()
            break;
        case 1:
            alert(ShopMessage)
            break;
        case -1:
            alert("系统出错")
            break;

        default:
            alert("添加失败,重来")
            break;
    }
}
// 删除
const delectShop = async (id: number) => {
    await deleteProduct(id)
    if (ShopCode === 0) {
        await all()
    }
}
// 放大预览
const handlePictureCardPreview = (URL: string) => {
    alert(URL)
}
const ruleFormRef = ref<FormInstance>()
const checkName = (rule: any, value: any, callback: any) => {
    if (!value) {
        return callback(new Error('请输入类型名称'))
    }
    // getByName(value)
    setTimeout(() => {
        if (ShopCode === 1) {
            callback()
        } else {
            callback(new Error("类型名重复"))
        }
    }, 1000)
}
// 表单模板
const ruleForm = reactive(<shop>{
    product_id: 0,
    name: '',
    price: 0,
    product_image: '',
    product_no: '',
    productType: <ShopType>{
        id: -1,
        name: '',
        status: -1
    }

})
// 验证规则
const rules = reactive<FormRules>({
    // name: [{ validator: checkName, trigger: 'blur' }],
})
// 修改提交
const submitInster = async (formEl: FormInstance | undefined) => {
    if (!formEl) return
    formEl.validate(async (valid) => {
        if (valid) {
            await ShopTypeRest.forEach(val => {
                if (val.name === ruleForm.productType.name) {
                    ruleForm.product_type_id = val.id
                }
            });
            console.log('ruleForm', ruleForm);
            await updateProductIng(<shop>{ product_id: ruleForm.product_id, product_no: ruleForm.product_no, name: ruleForm.name, price: ruleForm.price, product_image: ruleForm.product_image, product_type_id: ruleForm.product_type_id })
            // await updateProductIng(JSON.stringify(ruleForm))
            if (ShopCode == 0) {
                alert(ShopMessage)
                dialogVisibleInster.value = false;
                state.shop = <shop>{}
                loadingMesg.value = '刷新ing...'
                await all()
            } else {
                error.value = ShopMessage
                console.log('submit!')
            }

        } else {
            console.log('error submit!')
            return false
        }
    })
}
// 重置
const resetForm = (formEl: FormInstance | undefined) => {
    if (!formEl) return
    formEl.resetFields()
}
// 回退行为#
// 图片加载失败时的回退行为。
const errorHandler = () => true
const svg = `
        <path class="path" d="
          M 30 15
          L 28 17
          M 25.61 25.61
          A 15 15, 0, 0, 1, 15 30
          A 15 15, 0, 1, 1, 27.99 7.5
          L 15 15
        " style="stroke-width: 4px; fill: rgba(0, 0, 0, 0)"/>
      `
</script>
<style scoped>
body {
    margin: 0;
}

.example-showcase .el-loading-mask {
    z-index: 9;
}

h3 {
    color: brown;
}
</style>
    