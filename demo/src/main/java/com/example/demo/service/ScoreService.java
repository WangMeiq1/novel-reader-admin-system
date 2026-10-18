package com.example.demo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.Score;
import com.example.demo.entity.ScoreVO;
import java.util.Map;

public interface ScoreService {
    Page<ScoreVO> getScorePage(int pageNum, int pageSize, String keyword);
    boolean saveScore(Score score);
    boolean updateScore(Score score);
    boolean deleteScore(Integer scoreId);
    Map<String, Object> getStatistics(Integer courseId);
}
