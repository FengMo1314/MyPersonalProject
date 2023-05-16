package com.example.springbootweb.controller;

import com.example.springbootweb.pojo.LHResult;
import com.example.springbootweb.pojo.SYSUser;
import com.example.springbootweb.pojo.Users;
import com.example.springbootweb.service.SYSUserService;
import com.example.springbootweb.service.TokensService;
import com.example.springbootweb.service.UsersService;
import com.example.springbootweb.util.JwtUtil;
import com.example.springbootweb.util.TokenUtil;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@ResponseBody
@RequestMapping("/sys_register")
public class SYSRegisterController {
    @Autowired
    SYSUserService sysUserService;
    @Resource
    TokensService tokensService;
    Map<String, Object> rh;
    private SYSUser sysUser;


    @PostMapping("/user")
    @ResponseBody
    public LHResult register(@RequestBody Map<String, String> mesg) throws Exception {
        sysUser = new SYSUser();
        rh = new HashMap<>();
        sysUser.setLogin_name(mesg.get("username"));
        sysUser.setPassword(mesg.get("password"));
        System.out.println(sysUser.toString());
        if (sysUserService.getUserByLName(mesg.get("username")) != null) {
            return LHResult.others("用户已注册");
        }
        if (sysUserService.addUser(sysUser) != 1) {
            return LHResult.fail("添加失败");
        }
        SYSUser getU = sysUserService.getUserByLName(mesg.get("username"));
        String token = JwtUtil.createToken(getU.getLogin_name(), "user", getU.getUser_id());
        if (!TokenUtil.writeTokenToSQL(token, getU.getUser_id(), tokensService)) {
            return LHResult.fail("储存token失败");
        }
        rh.put("Authoritization", token);
        rh.put("type", "user");
        return LHResult.succ("注册OK", rh);

    }

    @GetMapping("/{username}")
    @ResponseBody
    public LHResult usersExist(@PathVariable("username") String username) {
        if (sysUserService.getUserByLName(username) == null) {
            return LHResult.succ("用户名不存在");
        }
        return LHResult.others("用户已存在");
    }
}
