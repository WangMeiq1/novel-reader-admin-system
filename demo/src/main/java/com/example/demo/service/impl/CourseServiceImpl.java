package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.Course;
import com.example.demo.mapper.CourseMapper;
import com.example.demo.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseMapper courseMapper;

    @Override
    public Page<Course> getCoursePage(int pageNum, int pageSize, String keyword) {
        Page<Course> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        if (StringUtils.hasText(keyword)) {
            wrapper.like("course_name", keyword);
        }
        wrapper.orderByDesc("course_id");
        return courseMapper.selectPage(page, wrapper);
    }

    @Override
    public boolean saveCourse(Course course) {
        return courseMapper.insert(course) > 0;
    }

    @Override
    public boolean updateCourse(Course course) {
        return courseMapper.updateById(course) > 0;
    }

    @Override
    public boolean deleteCourse(Integer courseId) {
        return courseMapper.deleteById(courseId) > 0;
    }
}