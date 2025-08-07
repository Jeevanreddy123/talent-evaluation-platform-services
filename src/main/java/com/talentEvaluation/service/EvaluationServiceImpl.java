
package com.talentEvaluation.service;

import com.talentEvaluation.dto.EvaluateCandidateDto;
import com.talentEvaluation.entity.Evaluation;
import com.talentEvaluation.repository.EvaluationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class EvaluationServiceImpl implements EvaluationService {

    @Autowired
    private EvaluationRepository evaluationRepository;

    @Override
    public Evaluation addEvaluation(Evaluation evaluation) {
        return evaluationRepository.save(evaluation);
    }

    @Override
    public List<Evaluation> getAllCandidates() {
        return evaluationRepository.findAll();
    }

    @Override
    public List<Evaluation> getCandidatesForEvaluator(Long associateId) {
        return evaluationRepository.findByEvaluatorAssociateId(associateId);
    }

    @Override
    public Evaluation uploadResume(MultipartFile file, Long candidateId) {
        try {
            Evaluation evaluation = evaluationRepository.findById(candidateId).orElseThrow(() -> new RuntimeException("Evaluation not found"));
            evaluation.setResume(file.getBytes());
            return evaluationRepository.save(evaluation);
        } catch (IOException e) {
            throw new RuntimeException("Could not upload the file: " + e.getMessage());
        }
    }

    @Override
    public byte[] downloadResume(Long candidateId) {
        Evaluation evaluation = evaluationRepository.findById(candidateId).orElseThrow(() -> new RuntimeException("Evaluation not found"));
        return evaluation.getResume();
    }

    @Override
    public Evaluation updateEvaluation(EvaluateCandidateDto dto) {
        Evaluation evaluation = evaluationRepository.findById(dto.getCandidateId()).orElseThrow(() -> new RuntimeException("Evaluation not found"));
        evaluation.setScore(dto.getScore());
        evaluation.setNotes(dto.getNotes());
        return evaluationRepository.save(evaluation);
    }
}
