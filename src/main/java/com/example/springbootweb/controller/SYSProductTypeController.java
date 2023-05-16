package com.example.springbootweb.controller;

import com.example.springbootweb.Constant.Constant;
import com.example.springbootweb.pojo.LHResult;
import com.example.springbootweb.pojo.SYSProductType;
import com.example.springbootweb.service.SYSProductTypeService;
import com.example.springbootweb.util.LHAuth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品类型表控制
 */
@LHAuth({"1","0"})
@RestController
@RequestMapping("/sys_product_type")
@Slf4j//日志打印
public class SYSProductTypeController {
    @Autowired
    private SYSProductTypeService sysProductTypeService;
    private SYSProductType productType;
    private Map<String,Object> rm;
//    @LHAuth(user = "1")
    @GetMapping
    @ResponseBody
    public LHResult getAllProductType(){
        rm=new HashMap<>();
        List<SYSProductType> list=sysProductTypeService.getAllProductType();
        rm.put("allproducttype",list);
        return LHResult.succ("获取全部商品列表信息成功",rm);
    }
//    @LHAuth(user = "1")
    @PostMapping
    @ResponseBody
    public LHResult addProductType(@RequestBody Map<String,String> mesg){
        String typeName=mesg.get("name");
        productType=sysProductTypeService.getProductTypeByName(typeName);
        if (productType!=null){
            return LHResult.others("商品名称已存在");
        }
        SYSProductType type=new SYSProductType(null,typeName,Constant.TABLE_TRUE);
       if (sysProductTypeService.addProductType(type)<=0){
           return LHResult.fail("入库失败");
       }
        return LHResult.succ("添加OK");
    }
//    @LHAuth(user = "1")
    @PutMapping
    @ResponseBody
    public LHResult updateProductType(@RequestParam Map<String, String> mesg){
        System.out.println("更新输入:\t"+mesg);
        Integer id= Integer.valueOf(mesg.get("id"));
        String name=mesg.get("name");
        Integer status= Integer.valueOf(mesg.get("status"));
        System.out.println("["+id+";"+name+";"+status+"]");
        productType=sysProductTypeService.getProductTypeByName(name);
        System.out.println("pp"+productType);
        if (productType!=null){
            if (!productType.getId().equals(id)) {
                return LHResult.others("类型名称已存在");
            }
        }
        productType=new SYSProductType(id,name,status);
        Integer roe=null;
//            try {
        roe=sysProductTypeService.updateProductTypeById(productType);
        if (roe<=0){
            return LHResult.fail("更新库失败");
        }
        System.out.println("TT:\t"+productType);
        return LHResult.succ("更新成功");

//            }catch (RuntimeException e){
//                 rm=new HashMap<>();
//                 rm.put("",e.getLocalizedMessage());
//                return LHResult.fail("数据库——更新shopType错误",rm);
//            }
    }
//    @LHAuth(user = "1")
    @GetMapping("/{name}")
    @ResponseBody
    public LHResult getProductTypeByName(@PathVariable("name") String name){
        rm=new HashMap<>();
        System.out.println("输入name\t"+name);
        productType=sysProductTypeService.getProductTypeByName(name);
        if (productType==null){
            return LHResult.others("无此类型");
        }
        rm.put("producttype",productType);
        return LHResult.succ("类型名查询成功",rm);
    }

}
