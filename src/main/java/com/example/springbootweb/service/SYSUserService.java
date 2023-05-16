package com.example.springbootweb.service;

import com.example.springbootweb.pojo.SYSUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface SYSUserService {
    void fishUsersId();

    List<SYSUser> getAllUsers();

    SYSUser getUserByUName(String userName);
    SYSUser getUserByLName(String loginName);

    SYSUser getUserByUId(Integer userId);

    Integer addUser(SYSUser sysUser);

    Integer addUsers(List<SYSUser> usersList);

    Integer deleteUserById(Integer userId);

    Integer deleteUserByIds(Integer[] ids);

    Integer updataUserById(SYSUser sysUser);

    Integer updataUserByIds(List<SYSUser> usersList);
}
