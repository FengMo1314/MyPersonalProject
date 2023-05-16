package com.example.springbootweb.dao;
import com.example.springbootweb.pojo.Users;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface UsersDao {
    void fishUsersId();

    List<Users> getAllUsers();

    Users getUserByUName(@Param("users") Users users);

    Users getUserByUId(@Param("users") Users users);

    Integer addUser(@Param("users") Users users);

    Integer addUsers(@Param("usersList") List<Users> usersList);

    Integer deleteUserById(@Param("users") Users users);

    Integer deleteUserByIds(@Param("ids") Integer[] ids);

    Integer updataUserById(@Param("users") Users users);

    Integer updataUserByIds(@Param("ids") List<Users> usersList);
}
