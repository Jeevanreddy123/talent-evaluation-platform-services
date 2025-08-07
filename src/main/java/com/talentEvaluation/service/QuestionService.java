
package com.talentEvaluation.service;

import com.talentEvaluation.entity.Question;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface QuestionService {
    void save(MultipartFile file, String technology);
    void deleteQuestionByTechnology(String technology);
    List<Question> getQuestionsByLevelAndTechnology(String level, String technology);
}
