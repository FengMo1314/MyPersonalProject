package com.example.springbootweb.controller;
import com.example.springbootweb.pojo.LHResult;
import com.example.springbootweb.pojo.Users;
import com.example.springbootweb.service.UsersService;
import com.example.springbootweb.util.JwtUtil;
import com.example.springbootweb.util.LHAuth;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.*;


/*
 * @Controller类中的方法可以直接通过返回String跳转到jsp、ftl、html等模版页面。 在方法上加@ResponseBody注解，也可以返回实体对象。
 * @RestController类中的所有方法只能返回String、Object、Json等实体对象，不能跳转到模版页面。
 */
@LHAuth("root")
@RestController
@Controller
@RequestMapping("/user")
@Slf4j//日志打印
public class UsersController {
    @Autowired
    private UsersService usersService;
    private HashMap<String, Object> rh;//信息返回规范
    private Users restUsers;
    private List<Users> restUsersListMain;

    @GetMapping
    @ResponseBody
    public LHResult getAllUsers() {
        rh = new HashMap<>();
        restUsersListMain = usersService.getAllUsers();
        rh.put("allUsers", restUsersListMain);
        return LHResult.succ("Select All Users Is True", rh);
    }

    @GetMapping("/{currentPage}/{pageSize}")
    @ResponseBody
    public LHResult getSomeUsers(@PathVariable("currentPage") Integer currentPage,
                                 @PathVariable("pageSize") Integer pageSize) {
        rh = new HashMap<>();
        PageHelper.startPage(currentPage, pageSize);
        restUsersListMain = usersService.getAllUsers();
        PageInfo<Users> pageInfo = new PageInfo<>(restUsersListMain);
        rh.put("somUsers", pageInfo);
        return LHResult.succ("Select Some Users By Page is true", rh);
    }
    @GetMapping("/{usertype}")
    @ResponseBody
    public LHResult getUserByUsername(@PathVariable("usertype") String usertype, @RequestHeader("Authorization") String token) throws Exception {
        String uName = JwtUtil.verifyToken(token).get("username").asString();
        Integer uId = JwtUtil.verifyToken(token).get("uId").asInt();
        rh = new HashMap<>();
        restUsers = new Users();
        restUsers.setUsername(uName);
        restUsers.setType(usertype);
        restUsers.setId(uId);
        System.out.println("通过用户名查用户" + restUsers);
        Users getU = usersService.getUserByUName(restUsers);
        if (getU == null) {
            return LHResult.others("用户不存在");
        }
        rh.put("restUsers", getU);
        return LHResult.succ(rh);
    }
    @LHAuth
    @GetMapping("/id")
    @ResponseBody
    public LHResult getUserById(@RequestHeader("Authorization") String token) throws Exception {
        Integer uId = JwtUtil.verifyToken(token).get("uId").asInt();
        rh = new HashMap<>();
        restUsers = new Users();
        restUsers.setId(uId);
        System.out.println("通过用户名查用户" + restUsers);
        Users getU = usersService.getUserByUId(restUsers);
        if (getU == null) {
            return LHResult.others("用户不存在");
        }
        rh.put("restUsers", getU);
        return LHResult.succ(rh);
    }

    @PostMapping
    @ResponseBody
    public LHResult addUser(@RequestBody Users user) {
        rh = new HashMap<>();
        System.out.println("添加用户信息" + user);
        restUsers = usersService.getUserByUName(user);
        if (restUsers == null) {
            if (usersService.addUser(user) <= 0) {
                return LHResult.fail("addIsFalse");
            }
            return LHResult.succ("addIsTrue");
        } else {
            return LHResult.others("Username has True");
        }
    }

    @PostMapping("/users")
    @ResponseBody
    public LHResult addUsers(@RequestBody ArrayList<Users> usersList) {

        rh = new HashMap<>();
        restUsersListMain = new ArrayList<>();
        System.out.println("添加用户信息" + usersList);
        List<Users> ar = new ArrayList<>();
        for (Users u : usersList) {//筛选用户
            restUsers = usersService.getUserByUName(u);
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
            if (usersService.addUsers(ar) <= 0) {//数据库操作失误
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
        restUsers = new Users();
        restUsers.setId(userId);
        if (usersService.deleteUserById(restUsers) <= 0) {
            return LHResult.fail("deleteIsFalse");
        }
        return LHResult.succ("deleteIsTrue");
    }
    @DeleteMapping("/ids/{ids}")
    @ResponseBody
    public LHResult deleteUserByIds(@PathVariable("ids") Integer[] userIds) {
        System.out.println("删除用户id" + Arrays.toString(userIds));
        if (usersService.deleteUserByIds(userIds) <= 0) {
            return LHResult.fail("deleteIsFalse");
        }
        return LHResult.succ("deleteIsTrue" + usersService.deleteUserByIds(userIds));
    }
    @LHAuth
    @PutMapping
    @ResponseBody
    public LHResult updataUserById(@RequestParam Map<String, String> user) {
        Users getU = new Users();
        getU.setEmail(user.get("email"));
        getU.setPhone(user.get("phone"));
        getU.setUsername(user.get("username"));
        getU.setPassword(user.get("password"));
        getU.setId(Integer.valueOf(user.get("id")));
        getU.setType(user.get("type"));
        System.out.println("更新用户\n" + user);
        restUsers = usersService.getUserByUName(getU);//查看用户名是否重复
        if (restUsers == null) {//用户名不存在
            Integer upId = usersService.updataUserById(getU);
            if (upId <= 0) {
                return LHResult.fail("updataIsFalse");
            }
        } else {//用户名已存在——判断是否为自己修改自己
            if (!restUsers.getId().equals(getU.getId())) {
                return LHResult.others("updataIsFalse:usernameHasTrue");
            }
            Integer upId = usersService.updataUserById(getU);
            if (upId <= 0) {
                return LHResult.fail("updataIsFalse");
            }
        }
        return LHResult.succ("updataIsTrue");
    }
    @PutMapping("/ids")
    @ResponseBody
    public LHResult updataUserByIds(@RequestParam List<Users> userList) {
        System.out.println("更新用户\n" + userList);
        for (Users u : userList) {//筛选入库信息
            restUsers = usersService.getUserByUName(u);//查询重名
            if (restUsers != null) {//重名
                if (restUsers.getId().equals(u.getId())) {//不是自己更新
                    userList.remove(u);//删除该用户
                }
            }
        }
        Integer upId = usersService.updataUserByIds(userList);//查看用户名是否重复
        if (upId <= 0) {
            return LHResult.fail("Update Users Is False");
        }

        return LHResult.succ("Update Users Is True");
    }
    @GetMapping("/fish")
    @ResponseBody
    public LHResult fishUsersId() {
        usersService.fishUsersId();
        return LHResult.succ("fish is true");
    }
}
