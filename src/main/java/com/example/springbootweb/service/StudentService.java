package com.example.springbootweb.service;
import com.example.springbootweb.pojo.Student;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Transactional
public interface StudentService {
    void fishUsersId();

    List<Student> getAllStudent();

    Student getStudentByStuName(Student student);

    Student getStudentByStuId(Student student);

    Integer addStudent(Student student);

    Integer addStudents(List<Student> studentList);

    Integer deleteStudentById(Student student);

    Integer deleteStudentByIds(Integer[] ids);

    Integer updataStudentById(Student student);

    Integer updataStudentByIds(List<Student> studentList );
}
