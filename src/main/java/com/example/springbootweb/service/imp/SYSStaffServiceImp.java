package com.example.springbootweb.service.imp;

import com.example.springbootweb.dao.SYSStaffDao;
import com.example.springbootweb.pojo.SYSStaff;
import com.example.springbootweb.service.SYSStaffService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class SYSStaffServiceImp implements SYSStaffService {
    @Autowired
    private SYSStaffDao staffDao;
    private SYSStaff sysStaff;
    private Date date;
    @Override
    public void fishUsersId() {

    }

    @Override
    public List<SYSStaff> getAllUsers() {

        return staffDao.getAllUsers();
    }

    @Override
    public SYSStaff getUserByUName(String userName) {
        sysStaff=new SYSStaff();
        sysStaff.setStaff_name(userName);
        return staffDao.getUserByUName(sysStaff);
    }

    @Override
    public SYSStaff getUserByLName(String loginName) {
        sysStaff=new SYSStaff();
        sysStaff.setLogin_name(loginName);
        return staffDao.getUserByLName(sysStaff);
    }

    @Override
    public SYSStaff getUserByUId(Integer userId) {
        sysStaff=new SYSStaff();
        sysStaff.setStaff_id(userId);
        return staffDao.getUserByUId(sysStaff);
    }

    @Override
    public Integer addUser(SYSStaff sysStaff) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//注意月和小时的格式为两个大写字母
        date = new Date();//获得当前时间
        System.out.println("日期：\t"+date);
        sysStaff.setCreate_date(df.format(date));
        return staffDao.addUser(sysStaff);
    }

    @Override
    public Integer addUsers(List<SYSStaff> staffList) {
        for (SYSStaff s:staffList) {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//注意月和小时的格式为两个大写字母
            date = new Date();//获得当前时间
            s.setCreate_date(df.format(date));
        }
        return staffDao.addUsers(staffList);
    }

    @Override
    public Integer deleteUserById(Integer userId) {
        sysStaff=new SYSStaff();
        sysStaff.setStaff_id(userId);
        return staffDao.deleteUserById(sysStaff);
    }

    @Override
    public Integer deleteUserByIds(Integer[] ids) {
        return staffDao.deleteUserByIds(ids);
    }

    @Override
    public Integer updataUserById(SYSStaff sysStaff) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//注意月和小时的格式为两个大写字母
        date = new Date();//获得当前时间
        sysStaff.setCreate_date(df.format(date));
        return staffDao.updataUserById(sysStaff);
    }

    @Override
    public Integer updataUserByIds(List<SYSStaff> staffList) {
        return staffDao.updataUserByIds(staffList);
    }
}
