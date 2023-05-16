package com.example.springbootweb.controller;

import com.example.springbootweb.util.JwtUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/test", produces = "application/json;charset=utf-8")
public class Test {
    @GetMapping
    @ResponseBody
    public String token(@RequestHeader("Authorization") String token) throws Exception {
        System.out.println("token:\n" + JwtUtil.verifyToken(token));
        return JwtUtil.verifyToken(token).toString();
    }
}
