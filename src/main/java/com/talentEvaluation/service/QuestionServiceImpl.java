
package com.talentEvaluation.service;

import com.talentEvaluation.enums.DifficultyLevel;
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

    @Autowired
    private ExcelFileService excelFileService;

    @Override
    @Transactional
    public void replaceAllDataByTechnology(MultipartFile file, String technology, String uploadedBy) {
        // The @Transactional annotation ensures this whole block is atomic.
        // 1. Delete old data
        questionRepository.deleteByTechnology(technology);
        excelFileService.deleteFileByTechnology(technology);

        // 2. Save new questions from the uploaded file
        try {
            List<Question> questions = ExcelHelper.excelToQuestions(file.getInputStream(), technology);
            questionRepository.saveAll(questions);
        } catch (IOException e) {
            throw new RuntimeException("fail to store excel data: " + e.getMessage());
        }

        // 3. Save the new Excel file metadata
        excelFileService.saveExcel(file, technology, uploadedBy);
    }

    @Override
    @Transactional
    public void deleteAllDataByTechnology(String technology) {
        questionRepository.deleteByTechnology(technology);
        excelFileService.deleteFileByTechnology(technology);
    }

    @Override
    public List<Question> getQuestionsByLevelAndTechnology(DifficultyLevel level, String technology) {
        return questionRepository.findByDifficultyLevelAndTechnology(level, technology);
    }
}
