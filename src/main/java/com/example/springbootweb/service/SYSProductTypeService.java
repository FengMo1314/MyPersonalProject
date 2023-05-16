package com.example.springbootweb.service;

import com.example.springbootweb.pojo.SYSProductType;

import java.util.List;

public interface SYSProductTypeService{
    List<SYSProductType> getAllProductType();
    SYSProductType getProductTypeByName(String productTypeName);
    Integer addProductType(SYSProductType sysProductType);
    Integer updateProductTypeById(SYSProductType sysProductType);
}
