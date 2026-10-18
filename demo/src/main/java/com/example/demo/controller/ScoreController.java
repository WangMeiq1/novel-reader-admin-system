package com.example.demo.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.Score;
import com.example.demo.entity.ScoreVO;
import com.example.demo.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/score")
@CrossOrigin
public class ScoreController {

    @Autowired
    private ScoreService scoreService;

    @GetMapping("/page")
    public Map<String, Object> getPage(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "5") int pageSize,
            @RequestParam(required = false) String keyword) {
        Page<ScoreVO> page = scoreService.getScorePage(pageNum, pageSize, keyword);
        Map<String, Object> result = new HashMap<>();
        result.put("total", page.getTotal());
        result.put("records", page.getRecords());
        return result;
    }

    @PostMapping("/add")
    public boolean add(@RequestBody Score score) {
        return scoreService.saveScore(score);
    }

    @PutMapping("/update")
    public boolean update(@RequestBody Score score) {
        return scoreService.updateScore(score);
    }

    @DeleteMapping("/delete/{scoreId}")
    public boolean delete(@PathVariable Integer scoreId) {
        return scoreService.deleteScore(scoreId);
    }

    @GetMapping("/statistics")
    public Map<String, Object> getStatistics(@RequestParam Integer courseId) {
        return scoreService.getStatistics(courseId);
    }
}