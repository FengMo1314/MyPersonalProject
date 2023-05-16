package com.example.springbootweb.dao;

import com.example.springbootweb.pojo.Student;
import com.example.springbootweb.pojo.Users;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StudentDao {
    void fishUsersId();

    List<Student> getAllStudent();

    Student getStudentByStuName(@Param("student") Student student);

    Student getStudentByStuId(@Param("student") Student student);

    Integer addStudent(@Param("student") Student student);

    Integer addStudents(@Param("studentList") List<Student> studentList);

    Integer deleteStudentById(@Param("student") Student student);

    Integer deleteStudentByIds(@Param("ids") Integer[] ids);

    Integer updataStudentById(@Param("student") Student student);

    Integer updataStudentByIds(@Param("ids") List<Student> studentList );
}
