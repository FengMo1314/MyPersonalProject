package com.example.springbootweb.controller;

import com.example.springbootweb.pojo.LHResult;
import com.example.springbootweb.pojo.Tokens;
import com.example.springbootweb.service.TokensService;
import com.example.springbootweb.util.LHAuth;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
@LHAuth("root")
@Controller
@RestController
@RequestMapping("/tokens")
public class TokensController {
    @Resource
    private TokensService tokensService;
    //    private Map<String, Object> rm;//接口返回规范
    private HashMap<String, Object> rh;//信息返回规范
    private List<Tokens> tokensList;

    @GetMapping
    @ResponseBody
    public LHResult getAllTokens() {
        rh = new HashMap<>();
        tokensList = tokensService.getAllTokens();
        rh.put("allTokens", tokensList);
        return LHResult.succ("getAllUsers is true", rh);
    }
    @LHAuth
    @GetMapping("/{uId}")
    @ResponseBody
    public LHResult getTokensByUId(@PathVariable("uId") Integer uId) {
        rh = new HashMap<>();
        Tokens tokens = new Tokens();
        tokens.setUId(uId);
        tokensList = tokensService.getTokensByUsersId(tokens);
        rh.put("allTokens", tokensList);
        return LHResult.succ("Select Some Users By User Id Is True", rh);
    }

}
