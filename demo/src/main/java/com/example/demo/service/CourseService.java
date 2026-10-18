package com.example.demo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.Course;

public interface CourseService {
    Page<Course> getCoursePage(int pageNum, int pageSize, String keyword);
    boolean saveCourse(Course course);
    boolean updateCourse(Course course);
    boolean deleteCourse(Integer courseId);
}