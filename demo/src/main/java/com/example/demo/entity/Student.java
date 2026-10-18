package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDate;

@Data
@TableName("student")
public class Student {
    @TableId
    private String studentId;
    private String name;
    private String gender;
    private String className;
    private String phone;
    private LocalDate enrollDate;
}