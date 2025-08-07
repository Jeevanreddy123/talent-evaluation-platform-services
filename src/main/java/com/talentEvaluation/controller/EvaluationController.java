

package com.talentEvaluation.controller;

import com.talentEvaluation.dto.EvaluateCandidateDto;
import com.talentEvaluation.dto.EvaluationDto;
import com.talentEvaluation.entity.Evaluation;
import com.talentEvaluation.service.EvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/evaluation")
public class EvaluationController {

    @Autowired
    private EvaluationService evaluationService;

    @PostMapping("/add-evaluation")
    public ResponseEntity<Evaluation> addEvaluation(@RequestBody EvaluationDto evaluationDto) throws ParseException {
        Evaluation evaluation = new Evaluation();
        evaluation.setCandidateName(evaluationDto.getCandidateName());
        evaluation.setSkill(evaluationDto.getSkill());
        evaluation.setEvaluationDate(new SimpleDateFormat("yyyy-MM-dd").parse(evaluationDto.getEvaluationDate()));
        return ResponseEntity.ok(evaluationService.addEvaluation(evaluation));
    }

    @GetMapping("/getEvaluations/{associateId}")
    public ResponseEntity<List<Evaluation>> getEvaluations(@PathVariable Long associateId) {
        return ResponseEntity.ok(evaluationService.getCandidatesForEvaluator(associateId));
    }

    @PostMapping("/uploadResume/{candidateId}")
    public ResponseEntity<Evaluation> uploadResume(@RequestParam("file") MultipartFile file, @PathVariable Long candidateId) {
        return ResponseEntity.ok(evaluationService.uploadResume(file, candidateId));
    }

    @GetMapping("/downloadResume/{candidateId}")
    public ResponseEntity<byte[]> downloadResume(@PathVariable Long candidateId) {
        byte[] resume = evaluationService.downloadResume(candidateId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"resume.pdf\"")
                .body(resume);
    }

    @PutMapping("/update-evaluation")
    public ResponseEntity<Evaluation> updateEvaluation(@RequestBody EvaluateCandidateDto dto) {
        return ResponseEntity.ok(evaluationService.updateEvaluation(dto));
    }
}
