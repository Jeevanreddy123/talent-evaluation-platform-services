
package com.talentEvaluation.service;

import com.talentEvaluation.dto.GroupedEvaluationsResponse;
import com.talentEvaluation.dto.EvaluationDto;
import com.talentEvaluation.dto.ResumeResponse;
import com.talentEvaluation.dto.EvaluateCandidateDto;
import com.talentEvaluation.entity.Evaluation;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface EvaluationService {
    Evaluation addEvaluation(EvaluationDto evaluationDto);
    List<Evaluation> getAllCandidates();
    List<Evaluation> getCandidatesForEvaluator(Long associateId);
    Evaluation uploadResume(MultipartFile file, Long candidateId);
    ResumeResponse downloadResume(Long candidateId);
    Evaluation updateEvaluation(EvaluateCandidateDto dto);
    GroupedEvaluationsResponse getGroupedEvaluations(int page, int size);
}
