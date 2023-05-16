package com.example.springbootweb.controller;

import com.example.springbootweb.pojo.LHResult;
import com.example.springbootweb.pojo.SYSUser;
import com.example.springbootweb.pojo.Users;
import com.example.springbootweb.service.SYSUserService;
import com.example.springbootweb.service.UsersService;
import com.example.springbootweb.util.JwtUtil;
import com.example.springbootweb.util.LHAuth;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.*;


/**
 * 用户表控制
 */
@LHAuth("1")
@RestController
@RequestMapping("/sys_user")
@Slf4j//日志打印
public class SYSUsersController {
    @Autowired
    private SYSUserService sysUserService;
    private HashMap<String, Object> rh;//信息返回规范
    private SYSUser restUsers;
    private List<SYSUser> restUsersListMain;

    @GetMapping
    @ResponseBody
    public LHResult getAllUsers() {
        rh = new HashMap<>();
        restUsersListMain = sysUserService.getAllUsers();
        rh.put("allUsers", restUsersListMain);
        return LHResult.succ("Select All Users Is True", rh);
    }

    @GetMapping("/{currentPage}/{pageSize}")
    @ResponseBody
    public LHResult getSomeUsers(@PathVariable("currentPage") Integer currentPage,
                                 @PathVariable("pageSize") Integer pageSize) {
        rh = new HashMap<>();
        PageHelper.startPage(currentPage, pageSize);
        restUsersListMain = sysUserService.getAllUsers();
        PageInfo<SYSUser> pageInfo = new PageInfo<>(restUsersListMain);
        rh.put("somUsers", pageInfo);
        return LHResult.succ("Select Some Users By Page is true", rh);
    }

    @GetMapping("/{usertype}")
    @ResponseBody
    public LHResult getUserByUsername(@PathVariable("usertype") String usertype, @RequestHeader("Authorization") String token) throws Exception {
        String uName = JwtUtil.verifyToken(token).get("username").asString();
        rh = new HashMap<>();
        restUsers=new SYSUser();
//        restUsers.setLogin_name(uName);
//        restUsers.setUser_id(uId);
        System.out.println("通过用户名查用户" + restUsers);
        restUsers= sysUserService.getUserByUName(uName);
        if (restUsers== null) {
            return LHResult.others("用户不存在");
        }
        rh.put("restUsers", restUsers);
        return LHResult.succ(rh);
    }

    @GetMapping("/id")
    @ResponseBody
    public LHResult getUserById(@RequestHeader("Authorization") String token) throws Exception {
        Integer uId = JwtUtil.verifyToken(token).get("uId").asInt();
        rh = new HashMap<>();
        restUsers = new SYSUser();
        System.out.println("通过用户名查用户" + restUsers);
        restUsers= sysUserService.getUserByUId(uId);
        if (restUsers == null) {
            return LHResult.others("用户不存在");
        }
        rh.put("restUsers", restUsers);
        return LHResult.succ(rh);
    }

    @PostMapping
    @ResponseBody
    public LHResult addUser(@RequestBody SYSUser sysUser) {
        System.out.println("添加用户信息" + sysUser);
        restUsers =sysUserService.getUserByUName(sysUser.getLogin_name());
        if (restUsers == null) {
            if (sysUserService.addUser(sysUser) <= 0) {
                return LHResult.fail("addIsFalse");
            }
            return LHResult.succ("addIsTrue");
        } else {
            return LHResult.others("Username has True");
        }
    }

    @PostMapping("/users")
    @ResponseBody
    public LHResult addUsers(@RequestBody ArrayList<SYSUser> usersList) {

        rh = new HashMap<>();
        restUsersListMain = new ArrayList<>();
        System.out.println("添加用户信息" + usersList);
        List<SYSUser> ar = new ArrayList<>();
        for (SYSUser u : usersList) {//筛选用户
            restUsers =sysUserService.getUserByLName(u.getLogin_name());
            if (restUsers != null) {//用户名重复
                restUsersListMain.add(u);//添加进结果集合
            } else {
                ar.add(u);
            }
        }
//        入库操作
        if (ar.isEmpty()) {//两个集合完全相同——没有一个用户添加 ok
            rh.put("someHaveUsers", restUsersListMain);
            return LHResult.others("all username has True", rh);
        } else {
            if (sysUserService.addUsers(ar) <= 0) {//数据库操作失误
                rh.put("someHaveUsers", restUsersListMain);
                return LHResult.fail("add Is False", rh);
            }
        }
        rh.put("someHaveUsers", restUsersListMain);
        return LHResult.succ("addIsTrue", rh);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public LHResult deleteUserById(@PathVariable("id") Integer userId) {
        System.out.println("删除用户id" + userId);
        if (sysUserService.deleteUserById(userId) <= 0) {
            return LHResult.fail("deleteIsFalse");
        }
        return LHResult.succ("deleteIsTrue");
    }

    @DeleteMapping("/ids/{ids}")
    @ResponseBody
    public LHResult deleteUserByIds(@PathVariable("ids") Integer[] userIds) {
        System.out.println("删除用户id" + Arrays.toString(userIds));
        if (sysUserService.deleteUserByIds(userIds) <= 0) {
            return LHResult.fail("deleteIsFalse");
        }
        return LHResult.succ("deleteIsTrue" + sysUserService.deleteUserByIds(userIds));
    }

    @PutMapping
    @ResponseBody
    public LHResult updataUserById(@RequestParam Map<String, String> user) {
       SYSUser getU = new SYSUser();
        getU.setUser_name(user.get("user_name"));
        getU.setPhone(user.get("phone"));
        getU.setLogin_name(user.get("login_name"));
        getU.setPassword(user.get("password"));
        getU.setUser_id(Integer.valueOf(user.get("user_id")));
        getU.setAddress(user.get("address"));
        getU.setIs_valid(Integer.valueOf(user.get("is_valid")));
        getU.setRegist_date(user.get("regist_date"));
        System.out.println("更新用户\n" + user);
        restUsers =sysUserService.getUserByLName(getU.getLogin_name());//查看用户名是否重复

        if (restUsers == null) {
            Integer upId = sysUserService.updataUserById(getU);
            if (upId <= 0) {
                return LHResult.fail("updataIsFalse");
            }
        } else {//不为空——判断是否为自己修改自己
            if (!restUsers.getUser_id().equals(getU.getUser_id())) {
                return LHResult.others("updataIsFalse:usernameHasTrue");
            }
            Integer upId =sysUserService.updataUserById(getU);
            if (upId <= 0) {
                return LHResult.fail("updataIsFalse");
            }
        }
        return LHResult.succ("updataIsTrue");
    }

    @PutMapping("/ids")
    @ResponseBody
    public LHResult updataUserByIds(@RequestParam List<SYSUser> userList) {
        System.out.println("更新用户\n" + userList);
        for (SYSUser u : userList) {//筛选入库信息
            restUsers = sysUserService.getUserByUName(u.getLogin_name());//查询重名
            if (restUsers != null) {//重名
                if (restUsers.getUser_id().equals(u.getUser_id())) {//不是自己更新
                    userList.remove(u);//删除该用户
                }
            }
        }
        Integer upId =sysUserService.updataUserByIds(userList);//查看用户名是否重复
        if (upId <= 0) {
            return LHResult.fail("Update Users Is False");
        }

        return LHResult.succ("Update Users Is True");
    }

    @GetMapping("/fish")
    @ResponseBody
    public LHResult fishUsersId() {
        sysUserService.fishUsersId();
        return LHResult.succ("fish is true");
    }
}
