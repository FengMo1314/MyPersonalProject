package com.example.springbootweb.dao;

import com.example.springbootweb.pojo.SYSStaff;
import com.example.springbootweb.pojo.SYSUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SYSStaffDao {
    void fishUsersId();

    List<SYSStaff> getAllUsers();

    SYSStaff getUserByUName(@Param("users") SYSStaff sysStaff);
    SYSStaff getUserByLName(@Param("users") SYSStaff sysStaff);

    SYSStaff getUserByUId(@Param("users") SYSStaff sysStaff);

    Integer addUser(@Param("users") SYSStaff sysStaff);

    Integer addUsers(@Param("usersList") List<SYSStaff> usersList);

    Integer deleteUserById(@Param("users") SYSStaff sysStaff);

    Integer deleteUserByIds(@Param("ids") Integer[] ids);

    Integer updataUserById(@Param("users") SYSStaff sysStaff);

    Integer updataUserByIds(@Param("ids") List<SYSStaff> usersList);

}
