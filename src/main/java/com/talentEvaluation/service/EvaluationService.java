
package com.talentEvaluation.service;

import com.talentEvaluation.dto.GroupedEvaluationsResponse;
import com.talentEvaluation.dto.EvaluationDto;
import com.talentEvaluation.dto.ResumeResponse;
import com.talentEvaluation.dto.EvaluateCandidateDto;
import com.talentEvaluation.projection.EvaluationSummary;
import com.talentEvaluation.entity.Evaluation;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface EvaluationService {
    Evaluation addEvaluation(EvaluationDto evaluationDto);
    List<EvaluationSummary> getAllCandidates();
    List<EvaluationSummary> getCandidatesForEvaluator(Long associateId);
    Evaluation uploadResume(MultipartFile file, Long candidateId);
    ResumeResponse downloadResume(Long candidateId);
    Evaluation updateEvaluation(EvaluateCandidateDto dto);
    GroupedEvaluationsResponse getGroupedEvaluations(int page, int size);
}
