package com.example.demo.entity;

import lombok.Data;

@Data
public class ScoreVO {
    private Integer scoreId;
    private String studentId;
    private String studentName;    // 学生姓名
    private String gender;
    private String className;
    private Integer courseId;
    private String courseName;     // 课程名称
    private Integer credit;
    private String teacher;
    private Integer score;
}