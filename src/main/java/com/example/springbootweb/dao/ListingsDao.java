package com.example.springbootweb.dao;


import com.example.springbootweb.pojo.Listings;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ListingsDao {
    List<Listings> getAllListings(@Param("mesg") String mesg);
}
