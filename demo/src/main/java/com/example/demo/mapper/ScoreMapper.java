package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.Score;
import com.example.demo.entity.ScoreVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface ScoreMapper extends BaseMapper<Score> {

    @Select("SELECT s.score_id, s.student_id, s.course_id, s.score, " +
            "st.name as student_name, st.gender, st.class_name, " +
            "c.course_name, c.credit, c.teacher " +
            "FROM score s " +
            "LEFT JOIN student st ON s.student_id = st.student_id " +
            "LEFT JOIN course c ON s.course_id = c.course_id " +
            "ORDER BY s.score_id DESC")
    List<ScoreVO> getAllScoreWithInfo();
}