

package com.talentEvaluation.controller;

import com.talentEvaluation.dto.ApiResponse;
import com.talentEvaluation.dto.GroupedEvaluationsResponse;
import com.talentEvaluation.dto.ResumeResponse;
import com.talentEvaluation.dto.EvaluateCandidateDto;
import com.talentEvaluation.dto.EvaluationDto;
import com.talentEvaluation.projection.EvaluationSummary;
import com.talentEvaluation.entity.Evaluation;
import com.talentEvaluation.service.EvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/evaluation")
public class EvaluationController {

    @Autowired
    private EvaluationService evaluationService;

    @PostMapping("/add-evaluation")
    public ResponseEntity<Void> addEvaluation(@RequestBody EvaluationDto evaluationDto) {
        Evaluation savedEvaluation = evaluationService.addEvaluation(evaluationDto);
        // Return 201 Created with a Location header pointing to the new resource
        URI location = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/evaluation/{id}")
                .buildAndExpand(savedEvaluation.getCandidateId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/getEvaluations/{associateId}")
    public ResponseEntity<List<EvaluationSummary>> getEvaluations(@PathVariable Long associateId) {
        return ResponseEntity.ok(evaluationService.getCandidatesForEvaluator(associateId));
    }

    @PostMapping("/uploadResume/{candidateId}")
    public ResponseEntity<ApiResponse> uploadResume(@RequestParam("file") MultipartFile file, @PathVariable Long candidateId) {
        evaluationService.uploadResume(file, candidateId);
        return ResponseEntity.ok(new ApiResponse("Resume uploaded successfully."));
    }

    @GetMapping("/downloadResume/{candidateId}")
    public ResponseEntity<byte[]> downloadResume(@PathVariable Long candidateId) {
        ResumeResponse resumeResponse = evaluationService.downloadResume(candidateId);
        String filename = "resume" + getFileExtension(resumeResponse.getFileType());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, resumeResponse.getFileType())
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .body(resumeResponse.getFileData());
    }

    @PutMapping("/update-evaluation")
    public ResponseEntity<Void> updateEvaluation(@RequestBody EvaluateCandidateDto dto) {
        evaluationService.updateEvaluation(dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/grouped")
    public ResponseEntity<GroupedEvaluationsResponse> getGroupedEvaluations(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        return ResponseEntity.ok(evaluationService.getGroupedEvaluations(page, size));
    }

    private String getFileExtension(String mimeType) {
        if (mimeType == null) {
            return ".bin"; // default extension
        }
        switch (mimeType) {
            case "application/pdf":
                return ".pdf";
            case "application/msword":
                return ".doc";
            case "application/vnd.openxmlformats-officedocument.wordprocessingml.document":
                return ".docx";
            default:
                return ".bin";
        }
    }
}
