package com.example.springbootweb.controller;

import com.example.springbootweb.pojo.Avatar;
import com.example.springbootweb.pojo.LHResult;
import com.example.springbootweb.service.AvatarService;
import com.example.springbootweb.util.JwtUtil;
import com.example.springbootweb.util.LHAuth;
import com.example.springbootweb.util.UrlUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * 头像表控制层
 */
@LHAuth("root")
@RestController
@Controller
@RequestMapping("/avatar")
@Slf4j//日志打印
public class AvatarController {
    @Resource
    private AvatarService avatarService;
    private HashMap<String, Object> rh;
    private Avatar avatar;
    @LHAuth
    @GetMapping
    @ResponseBody
    public LHResult getAvatar(@RequestHeader("Authorization") String token, HttpServletRequest request) throws Exception {
        Integer uId= JwtUtil.parseToken(token).get("uId").asInt();
        avatar=avatarService.gettByUId(uId);
        if (avatar==null){
            return LHResult.others("没有库存");
        }
        rh=new HashMap<>();
        String imgUrl= UrlUtil.doUrlAboutStart(request,avatar.getImgUrl());
        rh.put("imgUrl",imgUrl);
        return LHResult.succ("头像获取成功",rh);
    }
}
