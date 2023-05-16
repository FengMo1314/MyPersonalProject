package com.example.springbootweb.dao;

import com.example.springbootweb.pojo.SYSUser;
import com.example.springbootweb.pojo.Users;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SYSUserDao {
    void fishUsersId();

    List<SYSUser> getAllUsers();

    SYSUser getUserByUName(@Param("users") SYSUser sysUser);
    SYSUser getUserByLName(@Param("users") SYSUser sysUser);

    SYSUser getUserByUId(@Param("users") SYSUser sysUser);

    Integer addUser(@Param("users") SYSUser sysUser);

    Integer addUsers(@Param("usersList") List<SYSUser> usersList);

    Integer deleteUserById(@Param("users") SYSUser sysUser);

    Integer deleteUserByIds(@Param("ids") Integer[] ids);

    Integer updataUserById(@Param("users") SYSUser sysUser);

    Integer updataUserByIds(@Param("ids") List<SYSUser> usersList);
}
