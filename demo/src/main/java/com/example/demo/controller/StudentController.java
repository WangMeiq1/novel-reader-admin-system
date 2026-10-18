package com.example.demo.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.Student;
import com.example.demo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/student")
@CrossOrigin
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/page")
    public Map<String, Object> getPage(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "5") int pageSize,
            @RequestParam(required = false) String keyword) {
        Page<Student> page = studentService.getStudentPage(pageNum, pageSize, keyword);
        Map<String, Object> result = new HashMap<>();
        result.put("total", page.getTotal());
        result.put("records", page.getRecords());
        return result;
    }

    @PostMapping("/add")
    public boolean add(@RequestBody Student student) {
        return studentService.saveStudent(student);
    }

    @PutMapping("/update")
    public boolean update(@RequestBody Student student) {
        return studentService.updateStudent(student);
    }

    @DeleteMapping("/delete/{studentId}")
    public boolean delete(@PathVariable String studentId) {
        return studentService.deleteStudent(studentId);
    }

    @GetMapping("/get/{studentId}")
    public Student getById(@PathVariable String studentId) {
        return studentService.getStudentById(studentId);
    }
}
