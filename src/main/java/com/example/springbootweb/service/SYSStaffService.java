package com.example.springbootweb.service;

import com.example.springbootweb.pojo.SYSStaff;
import com.example.springbootweb.pojo.SYSUser;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface SYSStaffService {
    void fishUsersId();

    List<SYSStaff> getAllUsers();

    SYSStaff getUserByUName(String userName);
    SYSStaff getUserByLName(String loginName);

    SYSStaff getUserByUId(Integer userId);

    Integer addUser(SYSStaff sysStaff);

    Integer addUsers(List<SYSStaff> staffList);

    Integer deleteUserById(Integer userId);

    Integer deleteUserByIds(Integer[] ids);

    Integer updataUserById(SYSStaff sysStaff);

    Integer updataUserByIds(List<SYSStaff> staffList);
}
