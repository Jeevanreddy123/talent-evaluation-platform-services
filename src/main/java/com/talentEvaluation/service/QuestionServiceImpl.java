
package com.talentEvaluation.service;

import com.talentEvaluation.entity.Question;
import com.talentEvaluation.helper.ExcelHelper;
import com.talentEvaluation.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Override
    @Transactional
    public void save(MultipartFile file, String technology) {
        try {
            List<Question> questions = ExcelHelper.excelToQuestions(file.getInputStream(), technology);
            questionRepository.saveAll(questions);
        } catch (IOException e) {
            throw new RuntimeException("fail to store excel data: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public void deleteQuestionByTechnology(String technology) {
        questionRepository.deleteByTechnology(technology);
    }

    @Override
    public List<Question> getQuestionsByLevelAndTechnology(String level, String technology) {
        return questionRepository.findByDifficultyLevelAndTechnology(level, technology);
    }
}
