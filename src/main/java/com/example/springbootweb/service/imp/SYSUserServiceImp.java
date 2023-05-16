package com.example.springbootweb.service.imp;

import com.example.springbootweb.dao.SYSUserDao;
import com.example.springbootweb.pojo.SYSUser;
import com.example.springbootweb.service.SYSUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
@Service
@Transactional
public class SYSUserServiceImp implements SYSUserService {
    @Autowired
    private SYSUserDao sysUserDao;
    private SYSUser sysUser;
    private Date date;
    @Override
    public void fishUsersId() {

    }

    @Override
    public List<SYSUser> getAllUsers() {
        return sysUserDao.getAllUsers();
    }

    @Override
    public SYSUser getUserByUName(String userName) {
        sysUser=new SYSUser();
        sysUser.setUser_name(userName);
        return sysUserDao.getUserByUName(sysUser);
    }

    @Override
    public SYSUser getUserByLName(String loginName) {
        sysUser=new SYSUser();
        sysUser.setLogin_name(loginName);
        return sysUserDao.getUserByLName(sysUser);
    }

    @Override
    public SYSUser getUserByUId(Integer userId) {
        sysUser=new SYSUser();
        sysUser.setUser_id(userId);
        return sysUserDao.getUserByUId(sysUser);
    }

    @Override
    public Integer addUser(SYSUser sysUser) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//注意月和小时的格式为两个大写字母
        date = new Date();//获得当前时间
        System.out.println("日期：\t"+date);
        sysUser.setRegist_date(df.format(date));
        System.out.println("addUser:\t"+sysUser);
        return sysUserDao.addUser(sysUser);
    }

    @Override
    public Integer addUsers(List<SYSUser> usersList) {
        for (SYSUser su:usersList) {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//注意月和小时的格式为两个大写字母
            date = new Date();//获得当前时间
            System.out.println("日期：\t"+date);
            su.setRegist_date(df.format(date));
        }
        return sysUserDao.addUsers(usersList);
    }

    @Override
    public Integer deleteUserById(Integer userId)  {
       sysUser=new SYSUser();
       sysUser.setUser_id(userId);
        return sysUserDao.deleteUserById(sysUser);
    }

    @Override
    public Integer deleteUserByIds(Integer[] ids) {

        return sysUserDao.deleteUserByIds(ids);
    }

    @Override
    public Integer updataUserById(SYSUser sysUser) {
        return sysUserDao.updataUserById(sysUser);
    }

    @Override
    public Integer updataUserByIds(List<SYSUser> usersList) {
        return sysUserDao.updataUserByIds(usersList);
    }
}
