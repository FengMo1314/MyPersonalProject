package com.example.springbootweb.dao;

import com.example.springbootweb.pojo.SYSProductType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SYSProductTypeDao {
    List<SYSProductType> getAllProductType();
    SYSProductType getProductTypeByName(@Param("productType") SYSProductType sysProductType);
    Integer addProductType(@Param("productType") SYSProductType sysProductType);
    Integer updateProductTypeById(@Param("productType") SYSProductType sysProductType);
}
