
package com.talentEvaluation.controller;

import com.talentEvaluation.service.ExcelFileService;
import com.talentEvaluation.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin
@RequestMapping("/api/file")
public class ExcelFileController {

    @Autowired
    private ExcelFileService excelFileService;

    @Autowired
    private QuestionService questionService;

    @PostMapping
    @Transactional
    public ResponseEntity<Void> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("technology") String technology, @RequestParam("uploadedBy") String uploadedBy) {
        excelFileService.deleteFileByTechnology(technology);
        questionService.deleteQuestionByTechnology(technology);
        questionService.save(file, technology);
        excelFileService.saveExcel(file, technology, uploadedBy);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{technology}")
    @Transactional
    public ResponseEntity<Void> deleteFile(@PathVariable String technology) {
        excelFileService.deleteFileByTechnology(technology);
        questionService.deleteQuestionByTechnology(technology);
        return ResponseEntity.ok().build();
    }
}
