package com.example.springbootweb.service.imp;

import com.example.springbootweb.Constant.Constant;
import com.example.springbootweb.dao.SYSProductDao;
import com.example.springbootweb.pojo.SYSProduct;
import com.example.springbootweb.service.SYSProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
@Service
@Transactional
public class SYSProductServiceImp implements SYSProductService {
    @Autowired
    private SYSProductDao sysProductDao;
    private SYSProduct product;
    @Override
    public List<SYSProduct> getAll() {
        return sysProductDao.getAll();
    }

    @Override
    public List<SYSProduct> getAllProduct() {
        return sysProductDao.getAllProduct();
    }

    @Override
    public SYSProduct getAllProductByProductId(Integer productId) {
        product=new SYSProduct();
        product.setProduct_id(productId);
        return sysProductDao.getAllProductByProductId(product);
    }

    @Override
    public SYSProduct getAllByProductId(Integer productId) {
        product=new SYSProduct();
        product.setProduct_id(productId);
        return sysProductDao.getAllByProductId(product);
    }

    @Override
    public SYSProduct getAllProductByName(String name) {
        product=new SYSProduct();
        product.setName(name);
        return sysProductDao.getAllProductByName(product);
    }

    @Override
    public SYSProduct getAllByName(String name) {
        product=new SYSProduct();
        product.setName(name);
        return sysProductDao.getAllByName(product);
    }

    @Override
    public Integer addProduct(SYSProduct sysProduct) {
      Date  date = new Date();//获得当前时间
        sysProduct.setProduct_no("SP"+new SimpleDateFormat("yyyyMMddHHmmss").format(date));
        return sysProductDao.addProduct(sysProduct);
    }

    @Override
    public Integer updateProductByProductId(SYSProduct sysProduct) {
        return sysProductDao.updateProductByProductId(sysProduct);
    }

    @Override
    public Integer deleteProductByProductId(Integer productId) {
        product=new SYSProduct();
        product.setProduct_id(productId);
        return sysProductDao.deleteProductByProductId(product);
    }
}
