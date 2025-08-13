
package com.talentEvaluation.controller;

import com.talentEvaluation.enums.DifficultyLevel;
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
        try {
            DifficultyLevel levelEnum = DifficultyLevel.valueOf(level.toUpperCase());
            List<Question> questions = questionService.getQuestionsByLevelAndTechnology(levelEnum, technology);
            return ResponseEntity.ok(questions);
        } catch (IllegalArgumentException e) {
            // Returns a 400 Bad Request if the level string is not a valid enum constant
            return ResponseEntity.badRequest().build();
        }
    }
}
