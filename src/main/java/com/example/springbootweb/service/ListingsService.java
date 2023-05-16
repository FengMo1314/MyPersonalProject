package com.example.springbootweb.service;

import com.example.springbootweb.pojo.Listings;
import com.github.pagehelper.PageInfo;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ListingsService {
    PageInfo<Listings> getAllListings(Integer currentPage, Integer pageSize, String mesg);

}
