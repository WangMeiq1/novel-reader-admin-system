package com.example.demo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.Student;

public interface StudentService {
    Page<Student> getStudentPage(int pageNum, int pageSize, String keyword);
    boolean saveStudent(Student student);
    boolean updateStudent(Student student);
    boolean deleteStudent(String studentId);
    Student getStudentById(String studentId);
}