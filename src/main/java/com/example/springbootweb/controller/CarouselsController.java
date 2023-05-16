package com.example.springbootweb.controller;
import com.example.springbootweb.pojo.Carousels;
import com.example.springbootweb.pojo.LHResult;
import com.example.springbootweb.service.CarouseService;
import com.example.springbootweb.util.LHAuth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 走马灯url控制器
 */
@LHAuth("root")
@RestController
@RequestMapping("/carousels")
@Slf4j//日志打印
@Controller
public class CarouselsController {
    @Autowired
    private CarouseService carouseService;
    private Map<String, Object> rh;
    @LHAuth
    @GetMapping
    @ResponseBody
    public LHResult getAllCarouses() {
        rh = new HashMap<>();
        List<Carousels> allCarouses = carouseService.getAllCarousels();
        rh.put("allCarouses", allCarouses);
        return LHResult.succ("Select All Is True", rh);
    }

    @GetMapping("/{url}")
    @ResponseBody
    public LHResult getCarousesByUrl(@PathVariable("url") String url) {
        rh = new HashMap<>();
        Carousels carousels = new Carousels();
        carousels.setUrl(url);
        List<Carousels> someCarouses = carouseService.getOneCarouselsByUrl(carousels);
        rh.put("someCarouses", someCarouses);
        return LHResult.succ("Select One By Url Is True", rh);
    }
    @PostMapping
    @ResponseBody
    public LHResult addCarousels(@RequestBody Carousels carousels) {
        Integer addInt = carouseService.addCarousels(carousels);
        if (addInt <= 0) {
            return LHResult.others("Add One Is false");
        }
        return LHResult.succ("Add One Is True");
    }
    @PutMapping
    @ResponseBody
    public LHResult updateCarouselsById(@RequestParam Map<String, String> request) {
        Carousels carousels = new Carousels(Integer.parseInt(request.get("id")), request.get("url"));
        List<Carousels> getByUrl = carouseService.getOneCarouselsByUrl(carousels);
        if (getByUrl.isEmpty()) {
            Integer upId = carouseService.updataCarouselsById(carousels);
            if (upId <= 0) {
                return LHResult.fail("Updata One Is False");
            }
        } else {
            request.get("id");
            return LHResult.others("Updata One Is False-Url Has True");
        }
        return LHResult.succ("Updata One Is True");
    }
    @DeleteMapping("/{id}")
    @ResponseBody
    public LHResult deleteCarouselsById(@PathVariable("id") Integer id) {
        Carousels carousels = new Carousels();
        carousels.setId(id);
        Integer addInt = carouseService.deleteCarouselsById(carousels);
        if (addInt <= 0) {
            return LHResult.fail("Delete One Is False");
        }
        return LHResult.succ("Delete One Is True");
    }

    @DeleteMapping("/ids/{ids}")
    @ResponseBody
    public LHResult deleteCarouselsByIds(@PathVariable("ids") Integer[] ids) {

        Integer addInt = carouseService.deleteCarouselsByIds(ids);
        if (addInt <= 0) {
            return LHResult.fail("Delete Some Is False");
        }

        return LHResult.succ("Delete Some Is True");
    }


}
