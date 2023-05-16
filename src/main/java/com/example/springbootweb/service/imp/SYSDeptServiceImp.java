package com.example.springbootweb.service.imp;

import com.example.springbootweb.Constant.Constant;
import com.example.springbootweb.dao.SYSDeptDao;
import com.example.springbootweb.pojo.SYSDept;
import com.example.springbootweb.service.SYSDeptService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class SYSDeptServiceImp implements SYSDeptService {
    @Autowired
    private SYSDeptDao sysDeptDao;
    private SYSDept dept;
    private Date date;

    @Override
    public List<SYSDept> getAllDept() {
        return sysDeptDao.getAllDept();
    }

    @Override
    public List<SYSDept> getAllByFatherDeptId(Integer fatgetDeptId) {
        dept=new SYSDept();
        dept.setFather_dept_id(fatgetDeptId);
        return sysDeptDao.getAllByFatherDeptId(dept);
    }

    @Override
    public SYSDept getDeptByName(String deptName) {
        dept=new SYSDept();
        dept.setDept_name(deptName);
        return sysDeptDao.getDeptByName(dept);
    }

    @Override
    public SYSDept getDeptByDeptId(Integer deptId) {
        dept=new SYSDept();
        dept.setDept_id(deptId);
        return sysDeptDao.getDeptByDeptId(dept);
    }

    @Override
    public Integer addDept(SYSDept dept) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//注意月和小时的格式为两个大写字母
        date = new Date();//获得当前时间
        dept.setIs_valid(Constant.TABLE_TRUE);
        dept.setDept_no("BM"+new SimpleDateFormat("yyyyMMddHHmmss").format(date));
        dept.setCreate_date(df.format(date));
        return sysDeptDao.addDept(dept);
    }

    @Override
    public Integer updateDeptByDeptId(SYSDept inputDept) {
        return sysDeptDao.updateDeptByDeptId(inputDept);
    }
}
