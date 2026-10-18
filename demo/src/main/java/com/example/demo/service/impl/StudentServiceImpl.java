package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.Student;
import com.example.demo.mapper.StudentMapper;
import com.example.demo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public Page<Student> getStudentPage(int pageNum, int pageSize, String keyword) {
        Page<Student> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Student> wrapper = new QueryWrapper<>();
        if (StringUtils.hasText(keyword)) {
            wrapper.like("student_id", keyword)
                    .or().like("name", keyword);
        }
        wrapper.orderByDesc("student_id");
        return studentMapper.selectPage(page, wrapper);
    }

    @Override
    public boolean saveStudent(Student student) {
        return studentMapper.insert(student) > 0;
    }

    @Override
    public boolean updateStudent(Student student) {
        return studentMapper.updateById(student) > 0;
    }

    @Override
    public boolean deleteStudent(String studentId) {
        return studentMapper.deleteById(studentId) > 0;
    }

    @Override
    public Student getStudentById(String studentId) {
        return studentMapper.selectById(studentId);
    }
}