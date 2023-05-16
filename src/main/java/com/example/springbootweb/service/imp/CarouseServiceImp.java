package com.example.springbootweb.service.imp;

import com.example.springbootweb.dao.CarouselsDao;
import com.example.springbootweb.pojo.Carousels;
import com.example.springbootweb.service.CarouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CarouseServiceImp implements CarouseService {
    @Autowired
    private CarouselsDao carouselsDao;

    @Override
    public List<Carousels> getAllCarousels() {
        return carouselsDao.getAllCarousels();
    }

    @Override
    public List<Carousels> getOneCarouselsByUrl(Carousels carousels) {
        return carouselsDao.getOneCarouselsByUrl(carousels);
    }

    @Override
    public Integer updataCarouselsById(Carousels carousels) {
        return carouselsDao.updataCarouselsById(carousels);
    }

    @Override
    public Integer addCarousels(Carousels carousels) {
        return carouselsDao.addCarousels(carousels);
    }

    @Override
    public Integer deleteCarouselsById(Carousels carousels) {
        return carouselsDao.deleteCarouselsById(carousels);
    }

    @Override
    public Integer deleteCarouselsByIds(Integer[] ids) {
        return carouselsDao.deleteCarouselsByIds(ids);
    }

}
