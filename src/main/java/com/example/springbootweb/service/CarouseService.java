package com.example.springbootweb.service;



import com.example.springbootweb.pojo.Carousels;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface CarouseService {
    List<Carousels> getAllCarousels();

    List<Carousels> getOneCarouselsByUrl(Carousels carousels);

    Integer updataCarouselsById(Carousels carousels);

    Integer addCarousels(Carousels carousels);

    Integer deleteCarouselsById(Carousels carousels);

    Integer deleteCarouselsByIds(Integer[] ids);

}
