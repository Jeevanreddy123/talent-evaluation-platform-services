
package com.talentEvaluation.service;

import com.talentEvaluation.enums.DifficultyLevel;
import com.talentEvaluation.entity.Question;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface QuestionService {
    void replaceAllDataByTechnology(MultipartFile file, String technology, String uploadedBy);
    void deleteAllDataByTechnology(String technology);
    List<Question> getQuestionsByLevelAndTechnology(DifficultyLevel level, String technology);
}
