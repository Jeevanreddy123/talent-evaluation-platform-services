
package com.talentEvaluation.controller;

import com.talentEvaluation.dto.ApiResponse;
import com.talentEvaluation.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin
@RequestMapping("/api/file")
public class ExcelFileController {

    @Autowired
    private QuestionService questionService;

    @PostMapping
    public ResponseEntity<ApiResponse> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("technology") String technology, @RequestParam("uploadedBy") String uploadedBy) {
        questionService.replaceAllDataByTechnology(file, technology, uploadedBy);
        return ResponseEntity.ok(new ApiResponse("File uploaded successfully."));
    }

    @DeleteMapping("/{technology}")
    public ResponseEntity<Void> deleteFile(@PathVariable String technology) {
        questionService.deleteAllDataByTechnology(technology);
        return ResponseEntity.ok().build();
    }
}
