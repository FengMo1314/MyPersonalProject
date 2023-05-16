package com.example.springbootweb.controller;

import com.example.springbootweb.Constant.Constant;
import com.example.springbootweb.pojo.LHResult;
import com.example.springbootweb.pojo.SYSStaff;
import com.example.springbootweb.pojo.SYSUser;
import com.example.springbootweb.pojo.Users;
import com.example.springbootweb.service.SYSStaffService;
import com.example.springbootweb.service.SYSUserService;
import com.example.springbootweb.service.TokensService;
import com.example.springbootweb.util.JwtUtil;
import com.example.springbootweb.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/sys_login")
@Slf4j//日志打印
public class SYSLoginController {
    @Autowired
    private SYSUserService sysUserService;
    @Autowired
    private SYSStaffService sysStaffService;
    @Autowired
    private TokensService tokensService;
    private HashMap<String, Object> rh;
    private SYSUser sysUser;
    private SYSStaff sysStaff;

    @PostMapping("/user")
    @ResponseBody
    public LHResult loginUserByName(@RequestBody Map<String, String> mesg) throws Exception {
        rh = new HashMap<>();
        System.out.println("登录输入" + mesg);
        String loginUsername = mesg.get("username");
        sysUser = sysUserService.getUserByLName(loginUsername);
        if (sysUser != null) {
            String pass = sysUser.getPassword();//库存密码
            if (pass.equals(mesg.get("password"))) {
                String token;
                try {
                    token = JwtUtil.createToken(mesg.get("username"), "user", sysUser.getUser_id());
                    System.out.println("生成token\n" + token);

                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException("生成token失败");
                }
                if (!TokenUtil.writeTokenToSQL(token, sysUser.getUser_id(), tokensService)) {
                    throw new RuntimeException("储存token失败");
                }
                rh.put("Authoritization", token);
                rh.put("type", "user");
                return LHResult.succ("username:" + mesg.get("username") + ";passIsTrue", rh);
            } else {
                return LHResult.others("username:" + mesg.get("username") + ";passIsFalse");
            }
        } else {
            return LHResult.fail("username:" + mesg.get("username") + ";NoRegister");
        }
    }

    @PostMapping("/root")
    @ResponseBody
    public LHResult loginRootByName(@RequestBody Map<String, String> mesg){
        rh = new HashMap<>();
        System.out.println("登录输入" + mesg);
        String loginUsername = mesg.get("username");
        String loginPassword = mesg.get("password");
        String loginRole = mesg.get("role");
        sysStaff = sysStaffService.getUserByLName(loginUsername);
        if (sysStaff==null) {
            return LHResult.others("username:" + mesg.get("username") + "-用户不存在");
        }
        if (!sysStaff.getRole().equals(loginRole)) {
            return LHResult.others("角色不正确");
        }
        if (!sysStaff.getPassword().equals(loginPassword)) {
            return LHResult.others("username:" + mesg.get("username") + "-密码错误");
        }
        if (sysStaff.getIs_valid().equals(Constant.TABLE_FALSE)){
            return LHResult.others("username:" + mesg.get("username") + "-账户已被冻结");
        }
        String token=null;
        try {
            token = JwtUtil.createToken(mesg.get("username"), sysStaff.getRole(),sysStaff.getStaff_id());
            System.out.println("生成token\n" + token);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("生成token失败");
        }
        if (!TokenUtil.writeTokenToSQL(token,sysStaff.getStaff_id(), tokensService)) {
            throw new RuntimeException("储存token失败");
        }
        rh.put("Authoritization", token);
        rh.put("type",sysStaff.getRole());
        return LHResult.succ("username:" + mesg.get("username") + ";passIsTrue", rh);


    }

}
