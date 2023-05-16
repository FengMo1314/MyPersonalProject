package com.example.springbootweb.controller;

import com.example.springbootweb.dao.SYSProductDao;
import com.example.springbootweb.pojo.LHResult;
import com.example.springbootweb.pojo.SYSProduct;
import com.example.springbootweb.pojo.SYSProductType;
import com.example.springbootweb.service.SYSProductService;
import com.example.springbootweb.util.LHAuth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品表控制
 */
@LHAuth({"0","1"})
@RestController
@RequestMapping("/sys_product")
@Slf4j//日志打印
public class SYSProductController {
    @Autowired
    private SYSProductService sysProductService;
    private SYSProduct product;
    private Map<String,Object> rm;
    @GetMapping
    @ResponseBody
    public LHResult getAll(){
        List<SYSProduct> l = sysProductService.getAll();
        rm=new HashMap<>();
        rm.put("allproduct",l);
        return LHResult.succ("获取所有商品信息成功",rm);
    }
    @PostMapping
    @ResponseBody
    public LHResult addProduct(@RequestBody SYSProduct sysProduct){
        System.out.println("-----------------------------------------");
        System.out.println(sysProduct);
        System.out.println("-----------------------------------------");
        Integer row= sysProductService.addProduct(sysProduct);
        if (row<=0){
            return LHResult.fail("添加商品入库失败");
        }
        return LHResult.succ("添加商品成功");
    }
    @PutMapping
    @ResponseBody
    public LHResult updateProduct(@RequestParam Map<String,String> getMesg){
        Integer product_id= Integer.valueOf(getMesg.get("product_id"));
        String product_no=getMesg.get("product_no");
        String name=getMesg.get("name");
        Double price= Double.valueOf(getMesg.get("price"));
        String product_image=getMesg.get("product_image");
        Integer product_type_id= Integer.valueOf(getMesg.get("product_type_id"));

        product=new SYSProduct();
        product.setProduct_id(product_id);
        product.setProduct_image(product_image);
        product.setProduct_no(product_no);
        product.setName(name);
        product.setProduct_type_id(product_type_id);
        product.setPrice(price);
        System.out.println("-----------------product----------------------");
        System.out.println(product);
//        product.setProductType();
//        SYSProductType productType=getMesg.get("productType");
        Integer row= sysProductService.updateProductByProductId(product);
        if (row<=0){
            return LHResult.fail("更新商品入库失败");
        }
        return LHResult.succ("更新商品成功");
    }
    @DeleteMapping("/{id}")
    @ResponseBody
    public LHResult deleteProductByProductId(@PathVariable("id") Integer productId){
        Integer row= sysProductService.deleteProductByProductId(productId);
        if (row<=0){
            return LHResult.fail("删除商品信息入库失败");
        }
        return LHResult.succ("删除商品信息成功");
    }
}
