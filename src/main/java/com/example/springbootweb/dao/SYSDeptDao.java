package com.example.springbootweb.dao;

import com.example.springbootweb.pojo.SYSDept;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SYSDeptDao {
    List<SYSDept> getAllDept();
    List<SYSDept> getAllByFatherDeptId(@Param("dept") SYSDept dept);
    SYSDept getDeptByName(@Param("dept") SYSDept dept);
    SYSDept getDeptByDeptId(@Param("dept") SYSDept dept);
    Integer addDept(@Param("dept") SYSDept dept);
    Integer updateDeptByDeptId(@Param("dept") SYSDept dept);
}
