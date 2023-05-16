package com.example.springbootweb.service.imp;

import com.example.springbootweb.dao.SYSProductTypeDao;
import com.example.springbootweb.pojo.SYSProductType;
import com.example.springbootweb.service.SYSProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class SYSProductTypeServiceImp implements SYSProductTypeService {
    @Autowired
    private SYSProductTypeDao sysProductTypeDao;
    private SYSProductType sysProductType;
    @Override
    public List<SYSProductType> getAllProductType() {
        return sysProductTypeDao.getAllProductType();
    }

    @Override
    public SYSProductType getProductTypeByName(String productTypeName) {
        sysProductType=new SYSProductType();
        sysProductType.setName(productTypeName);
        return sysProductTypeDao.getProductTypeByName(sysProductType);
    }

    @Override
    public Integer addProductType(SYSProductType sysProductType) {
        return sysProductTypeDao.addProductType(sysProductType);
    }

    @Override
    public Integer updateProductTypeById(SYSProductType sysProductType) {
        return sysProductTypeDao.updateProductTypeById(sysProductType);
    }
}
