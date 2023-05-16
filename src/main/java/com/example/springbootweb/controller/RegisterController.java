package com.example.springbootweb.controller;
import com.example.springbootweb.pojo.LHResult;
import com.example.springbootweb.pojo.Users;
import com.example.springbootweb.service.TokensService;
import com.example.springbootweb.service.UsersService;
import com.example.springbootweb.util.JwtUtil;
import com.example.springbootweb.util.TokenUtil;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@ResponseBody
@RequestMapping("/register")
public class RegisterController {
    @Autowired
    UsersService usersService;
    @Resource
    TokensService tokensService;
    Map<String, Object> rh;

    @PostMapping
    @ResponseBody
    public LHResult register(@RequestBody Map<String, String> mesg) throws Exception {
        Users users = new Users();
        rh = new HashMap<>();
        users.setUsername(mesg.get("username"));
        users.setPassword(mesg.get("password"));
        users.setType(mesg.get("type"));
        if (usersService.addUser(users) <= 0) {
            return LHResult.fail("添加失败");
        } else {
            Users getU = usersService.getUserByUName(users);
            String token = JwtUtil.createToken(getU.getUsername(), getU.getType(), getU.getId());
            if (!TokenUtil.writeTokenToSQL(token, getU.getId(), tokensService)) {
                return LHResult.fail("储存token失败");
            }
            rh.put("Authoritization", token);
            rh.put("type", users.getType());
            return LHResult.succ("注册OK", rh);
        }
    }

    @GetMapping("/{username}")
    @ResponseBody
    public LHResult usersExist(@PathVariable("username") String username) {
        Users setUsers = new Users();
        setUsers.setUsername(username);
        if (usersService.getUserByUName(setUsers) == null) {
            return LHResult.succ("用户名不存在");
        }
        return LHResult.others("用户已存在");
    }
}
