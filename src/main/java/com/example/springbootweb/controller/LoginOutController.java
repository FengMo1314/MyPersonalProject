package com.example.springbootweb.controller;

import com.example.springbootweb.pojo.LHResult;
import com.example.springbootweb.pojo.Tokens;
import com.example.springbootweb.service.TokensService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@Controller
@RequestMapping("/loginOut")
@Slf4j//日志打印
public class LoginOutController {
    @Resource
    public TokensService tokensService;
    private HashMap<String, Object> rh;

    @GetMapping
    @ResponseBody
    public LHResult loginOut(@RequestHeader("Authorization") String token) {
        if (outToken(token)) {
            return LHResult.succ("LoginOut Is True");
        }
        return LHResult.others("LoginOut Is False");
    }

    public boolean outToken(String token) {
        Tokens setToken = new Tokens();
        setToken.setToken(token);
        Tokens getToken = tokensService.getTokensByToken(setToken);
        if (getToken==null){
            return true;
        }
        getToken.setActive("false");
        System.out.println("下线out" + getToken);
        return tokensService.updataTokensById(getToken) > 0;
    }
}
