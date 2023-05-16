package com.example.springbootweb.controller;

import com.example.springbootweb.pojo.Listings;
import com.example.springbootweb.pojo.LHResult;
import com.example.springbootweb.service.ListingsService;
import com.example.springbootweb.util.LHAuth;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
@LHAuth("root")
@RestController//返回json字符串
@Slf4j//日志打印
@RequestMapping("/listings")
@Controller
public class ListingsController {
    @Autowired
    private ListingsService listingsService;//这里注入接口，实际使用的是实现类
    private Map<String, Object> rm;
    @LHAuth
    @GetMapping
    @ResponseBody
    public LHResult getAllListings() {
        rm = new HashMap<>();
        PageInfo<Listings> listings = listingsService.getAllListings(1, 2, "");
        rm.put("somelistings", listings);
        return LHResult.succ("true", rm);
    }
    @LHAuth
    @PostMapping("/allListings/{currentPage}/{pageSize}")
    @ResponseBody
    public LHResult getAllListings(@PathVariable("currentPage") Integer currentPage,
                                   @PathVariable("pageSize") Integer pageSize,
                                   @RequestBody Map<String, String> mesg) {
        PageInfo<Listings> listings = listingsService.getAllListings(currentPage, pageSize, mesg.get("mesg"));
        System.out.println(mesg);
//        log.info("\n返回数据：{}\n",listings);
        log.debug("\n@PathVariable(\"currentPage\") Integer currentPage,\n" +
                "@PathVariable(\"pageSize\") Integer pageSize,\n" +
                "@RequestBody Map<String,String> mesg\n {}\n{}\n{}", currentPage, pageSize, mesg);
        rm = new HashMap<>();
        rm.put("allListings", listings);
        return LHResult.succ("Get AllListings By Page Is True", rm);
    }


}
