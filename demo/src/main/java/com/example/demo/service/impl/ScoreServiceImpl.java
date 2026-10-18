package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.Score;
import com.example.demo.entity.ScoreVO;
import com.example.demo.mapper.ScoreMapper;
import com.example.demo.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ScoreServiceImpl implements ScoreService {

    @Autowired
    private ScoreMapper scoreMapper;

    @Override
    public Page<ScoreVO> getScorePage(int pageNum, int pageSize, String keyword) {
        // 获取所有带关联信息的成绩数据
        List<ScoreVO> allList = scoreMapper.getAllScoreWithInfo();

        // 如果有关键字，进行过滤
        if (StringUtils.hasText(keyword)) {
            allList = allList.stream()
                    .filter(item ->
                            (item.getStudentId() != null && item.getStudentId().contains(keyword)) ||
                                    (item.getStudentName() != null && item.getStudentName().contains(keyword))
                    )
                    .collect(Collectors.toList());
        }

        // 手动分页
        int total = allList.size();
        int start = (pageNum - 1) * pageSize;
        int end = Math.min(start + pageSize, total);
        List<ScoreVO> pageList = start < total ? allList.subList(start, end) : new ArrayList<>();

        Page<ScoreVO> page = new Page<>(pageNum, pageSize);
        page.setTotal(total);
        page.setRecords(pageList);
        return page;
    }

    @Override
    public boolean saveScore(Score score) {
        if (score.getScore() < 0 || score.getScore() > 100) {
            return false;
        }
        return scoreMapper.insert(score) > 0;
    }

    @Override
    public boolean updateScore(Score score) {
        if (score.getScore() < 0 || score.getScore() > 100) {
            return false;
        }
        return scoreMapper.updateById(score) > 0;
    }

    @Override
    public boolean deleteScore(Integer scoreId) {
        return scoreMapper.deleteById(scoreId) > 0;
    }

    @Override
    public Map<String, Object> getStatistics(Integer courseId) {
        Map<String, Object> result = new HashMap<>();
        List<ScoreVO> allList = scoreMapper.getAllScoreWithInfo();

        // 按课程过滤
        List<ScoreVO> filtered = allList.stream()
                .filter(item -> item.getCourseId() != null && item.getCourseId().equals(courseId))
                .collect(Collectors.toList());

        if (filtered.isEmpty()) {
            result.put("count", 0);
            result.put("avg", 0);
            result.put("max", 0);
            result.put("min", 0);
            result.put("passRate", 0);
            return result;
        }

        int sum = 0;
        int max = 0;
        int min = 100;
        int passCount = 0;
        for (ScoreVO s : filtered) {
            int score = s.getScore();
            sum += score;
            if (score > max) max = score;
            if (score < min) min = score;
            if (score >= 60) passCount++;
        }
        double avg = (double) sum / filtered.size();
        double passRate = (double) passCount / filtered.size() * 100;

        result.put("count", filtered.size());
        result.put("avg", Math.round(avg * 100) / 100.0);
        result.put("max", max);
        result.put("min", min);
        result.put("passRate", Math.round(passRate * 100) / 100.0);
        return result;
    }
}