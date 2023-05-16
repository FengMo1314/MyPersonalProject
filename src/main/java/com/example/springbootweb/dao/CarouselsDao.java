package com.example.springbootweb.dao;

import com.example.springbootweb.pojo.Carousels;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CarouselsDao {
    List<Carousels> getAllCarousels();

    List<Carousels> getOneCarouselsByUrl(@Param("carousels") Carousels carousels);

    Integer updataCarouselsById(@Param("carousels") Carousels carousels);

    Integer addCarousels(@Param("carousels") Carousels carousels);

    Integer deleteCarouselsById(@Param("carousels") Carousels carousels);

    Integer deleteCarouselsByIds(@Param("ids") Integer[] ids);
}
