package com.example.springbootweb.service.imp;

import com.example.springbootweb.dao.ListingsDao;
import com.example.springbootweb.pojo.Listings;
import com.example.springbootweb.service.ListingsService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ListingsServiceImp implements ListingsService {
    @Autowired
    private ListingsDao listingsDao;

    @Override
    public PageInfo<Listings> getAllListings(Integer currentPage, Integer pageSize, String mesg) {
        if (pageSize >= 500) {
            pageSize = 500;
        }
        PageHelper.startPage(currentPage, pageSize);
        List<Listings> allListings = listingsDao.getAllListings(mesg);
        System.out.println("ListingsServiceImp:传入mesg-" + mesg);
//        PageInfo<Listings> pageInfo = new PageUtil<Listings>().getPage(currentPage, pageSize, allListings);
        PageInfo<Listings> pageInfo = new PageInfo<>(allListings);
        return pageInfo;

    }


}
