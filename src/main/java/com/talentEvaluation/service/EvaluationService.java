
package com.talentEvaluation.service;

import com.talentEvaluation.dto.EvaluateCandidateDto;
import com.talentEvaluation.entity.Evaluation;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface EvaluationService {
    Evaluation addEvaluation(Evaluation evaluation);
    List<Evaluation> getAllCandidates();
    List<Evaluation> getCandidatesForEvaluator(Long associateId);
    Evaluation uploadResume(MultipartFile file, Long candidateId);
    byte[] downloadResume(Long candidateId);
    Evaluation updateEvaluation(EvaluateCandidateDto dto);
}
