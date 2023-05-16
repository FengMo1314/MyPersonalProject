package com.example.springbootweb.service.imp;
import com.example.springbootweb.dao.UsersDao;
import com.example.springbootweb.pojo.Users;
import com.example.springbootweb.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UsersServiceImp implements UsersService {
    @Autowired
    private UsersDao usersDao;

    @Override
    public void fishUsersId() {
        usersDao.fishUsersId();
    }

    @Override
    public List<Users> getAllUsers() {
        return usersDao.getAllUsers();
    }

    @Override
    public Users getUserByUName(Users users) {
        return usersDao.getUserByUName(users);
    }

    @Override
    public Users getUserByUId(Users users) {
        return usersDao.getUserByUId(users);
    }

    @Override
    public Integer addUser(Users users) {
        return usersDao.addUser(users);
    }

    @Override
    public Integer addUsers(List<Users> usersList) {
        return usersDao.addUsers(usersList);
    }

    @Override
    public Integer deleteUserById(Users users) {
        return usersDao.deleteUserById(users);
    }

    @Override
    public Integer deleteUserByIds(Integer[] ids) {
        return usersDao.deleteUserByIds(ids);
    }

    @Override
    public Integer updataUserById(Users users) {
        return usersDao.updataUserById(users);
    }

    @Override
    public Integer updataUserByIds(List<Users> usersList) {
        return usersDao.updataUserByIds(usersList);
    }
}
