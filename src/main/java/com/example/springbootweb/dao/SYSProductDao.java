package com.example.springbootweb.dao;

import com.example.springbootweb.pojo.SYSProduct;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SYSProductDao {
   List<SYSProduct> getAll();
   List<SYSProduct> getAllProduct();
   SYSProduct getAllProductByProductId(@Param("product") SYSProduct sysProduct);
   SYSProduct getAllByProductId(@Param("product") SYSProduct sysProduct);
   SYSProduct getAllProductByName(@Param("product") SYSProduct sysProduct);
   SYSProduct getAllByName(@Param("product") SYSProduct sysProduct);
   Integer addProduct(@Param("product") SYSProduct sysProduct);
   Integer updateProductByProductId(@Param("product") SYSProduct sysProduct);
   Integer deleteProductByProductId(@Param("product") SYSProduct sysProduct);
}
