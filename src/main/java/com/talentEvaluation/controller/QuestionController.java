
package com.talentEvaluation.controller;

import com.talentEvaluation.entity.Question;
import com.talentEvaluation.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/level/{level}/technology/{technology}")
    public ResponseEntity<List<Question>> getQuestions(@PathVariable String level, @PathVariable String technology) {
        return ResponseEntity.ok(questionService.getQuestionsByLevelAndTechnology(level, technology));
    }
}
