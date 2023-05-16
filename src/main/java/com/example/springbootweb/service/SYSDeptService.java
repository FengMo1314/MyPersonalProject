package com.example.springbootweb.service;

import com.example.springbootweb.pojo.SYSDept;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface SYSDeptService {
    List<SYSDept> getAllDept();
    List<SYSDept> getAllByFatherDeptId(Integer fatgetDeptId);
    SYSDept getDeptByName(String deptName);
    SYSDept getDeptByDeptId(Integer deptId);
    Integer addDept(SYSDept dept);
    Integer updateDeptByDeptId(SYSDept dept);
}
