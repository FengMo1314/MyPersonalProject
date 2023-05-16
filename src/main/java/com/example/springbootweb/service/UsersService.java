package com.example.springbootweb.service;
import com.example.springbootweb.pojo.Users;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface UsersService {
    void fishUsersId();

    List<Users> getAllUsers();

    Users getUserByUName(Users users);

    Users getUserByUId(Users users);

    Integer addUser(Users users);

    Integer addUsers(List<Users> usersList);

    Integer deleteUserById(Users users);

    Integer deleteUserByIds(Integer[] ids);

    Integer updataUserById(Users users);

    Integer updataUserByIds(List<Users> usersList);
}
