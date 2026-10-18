package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("score")
public class Score {
    @TableId(type = IdType.AUTO)
    private Integer scoreId;
    private String studentId;
    private Integer courseId;
    private Integer score;
}