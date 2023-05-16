package com.example.springbootweb.controller;

import com.example.springbootweb.pojo.Avatar;
import com.example.springbootweb.pojo.LHResult;
import com.example.springbootweb.pojo.Users;
import com.example.springbootweb.service.AvatarService;
import com.example.springbootweb.service.TokensService;
import com.example.springbootweb.service.UsersService;
import com.example.springbootweb.util.JwtUtil;
import com.example.springbootweb.util.TokenUtil;
import com.example.springbootweb.util.UrlUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@Controller
@RequestMapping("/login")
@Slf4j//日志打印
public class LoginController {
    @Autowired
    private UsersService usersService;
    @Resource
    private TokensService tokensService;
    @Resource
    private AvatarService avatarService;
    //    private Map<String, Object> rm;
    private HashMap<String, Object> rh;

    @PostMapping
    @ResponseBody
    public LHResult loginByName(@RequestBody Map<String, String> mesg, HttpServletRequest request) throws Exception {

        rh = new HashMap<>();
        Users getU = new Users();
        getU.setUsername(mesg.get("username"));
        System.out.println("登录输入" + mesg);
        Users users = usersService.getUserByUName(getU);
        if (users != null) {
            String pass = users.getPassword();//库存密码
            if (pass.equals(mesg.get("password"))) {
                String token;
                try {
                    token = JwtUtil.createToken(mesg.get("username"), users.getType(), users.getId());
                    System.out.println("生成token\n" + token);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException("生成token失败");
                }
                if (!TokenUtil.writeTokenToSQL(token, users.getId(), tokensService)) {
                    throw new RuntimeException("储存token失败");
                }
                rh.put("Authoritization", token);
                rh.put("type", users.getType());
//                获取头像
                String imgUrl=null;
                Avatar avatar=avatarService.gettByUId(users.getId());
                if (avatar!=null){
                    imgUrl= UrlUtil.doUrlAboutStart(request,avatar.getImgUrl());
                    rh.put("imgUrl", imgUrl);
                }
                return LHResult.succ("username:" + mesg.get("username") + ";passIsTrue", rh);
            } else {
                return LHResult.others("username:" + mesg.get("username") + ";passIsFalse");
            }
        } else {
            return LHResult.others("username:" + mesg.get("username") + ";NoRegist");
        }
    }

}
