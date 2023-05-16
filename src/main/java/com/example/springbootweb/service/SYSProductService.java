package com.example.springbootweb.service;

import com.example.springbootweb.pojo.SYSProduct;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Transactional
public interface SYSProductService {
    List<SYSProduct> getAll();
    List<SYSProduct> getAllProduct();
    SYSProduct getAllProductByProductId(Integer productId);
    SYSProduct getAllByProductId(Integer productId);
    SYSProduct getAllProductByName(String name);
    SYSProduct getAllByName(String name);
    Integer addProduct(SYSProduct sysProduct);
    Integer updateProductByProductId(SYSProduct sysProduct);
    Integer deleteProductByProductId(Integer productId);
}
