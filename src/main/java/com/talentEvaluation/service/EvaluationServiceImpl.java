
package com.talentEvaluation.service;

import com.talentEvaluation.dto.EvaluationPage;
import com.talentEvaluation.dto.GroupedEvaluationsResponse;
import com.talentEvaluation.dto.EvaluationDto;
import com.talentEvaluation.dto.ResumeResponse;
import com.talentEvaluation.dto.EvaluateCandidateDto;
import com.talentEvaluation.enums.EvaluationStatus;
import com.talentEvaluation.exception.CandidateAlreadyAssignedException;
import com.talentEvaluation.entity.Evaluation;
import com.talentEvaluation.entity.User;
import com.talentEvaluation.repository.EvaluationRepository;
import com.talentEvaluation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class EvaluationServiceImpl implements EvaluationService {

    @Autowired
    private EvaluationRepository evaluationRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Evaluation addEvaluation(EvaluationDto evaluationDto) {
        if (evaluationRepository.existsById(evaluationDto.getCandidateId())) {
            throw new CandidateAlreadyAssignedException("Candidate is already assigned to an Evaluator");
        }

        User evaluator = userRepository.findById(evaluationDto.getEvaluatorId())
                .orElseThrow(() -> new RuntimeException("Evaluator not found with id: " + evaluationDto.getEvaluatorId()));

        Evaluation evaluation = new Evaluation();
        evaluation.setCandidateId(evaluationDto.getCandidateId());
        evaluation.setFirstName(evaluationDto.getFirstName());
        evaluation.setLastName(evaluationDto.getLastName());
        evaluation.setCandidateStack(evaluationDto.getCandidateStack());
        evaluation.setSoId(evaluationDto.getSoId());
        evaluation.setSoRole(evaluationDto.getSoRole());
        if (evaluationDto.getStatus() != null && !evaluationDto.getStatus().trim().isEmpty()) {
            try {
                evaluation.setStatus(EvaluationStatus.valueOf(evaluationDto.getStatus().toUpperCase()));
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Invalid status value provided: '" + evaluationDto.getStatus() + "'. Must be one of " + java.util.Arrays.toString(EvaluationStatus.values()));
            }
        }
        try {
            evaluation.setEvaluationDate(new SimpleDateFormat("yyyy-MM-dd").parse(evaluationDto.getEvaluationDate()));
        } catch (ParseException e) {
            throw new RuntimeException("Invalid date format for evaluationDate. Expected yyyy-MM-dd.", e);
        }
        evaluation.setEvaluator(evaluator);
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
            evaluation.setResumeFile(file.getBytes());
            evaluation.setResumeFileType(file.getContentType());
            return evaluationRepository.save(evaluation);
        } catch (IOException e) {
            throw new RuntimeException("Could not upload the file: " + e.getMessage());
        }
    }

    @Override
    public ResumeResponse downloadResume(Long candidateId) {
        Evaluation evaluation = evaluationRepository.findById(candidateId).orElseThrow(() -> new RuntimeException("Evaluation not found"));
        if (evaluation.getResumeFile() == null) {
            throw new RuntimeException("Resume not found for candidate: " + candidateId);
        }
        return new ResumeResponse(evaluation.getResumeFile(), evaluation.getResumeFileType());
    }

    @Override
    public Evaluation updateEvaluation(EvaluateCandidateDto dto) {
        Evaluation evaluation = evaluationRepository.findById(dto.getCandidateId()).orElseThrow(() -> new RuntimeException("Evaluation not found"));
        evaluation.setEvaluationDetails(dto.getEvaluationDetails());
        evaluation.setEvaluationFeedback(dto.getEvaluationFeedback());
        if (dto.getStatus() != null && !dto.getStatus().trim().isEmpty()) {
            try {
                evaluation.setStatus(EvaluationStatus.valueOf(dto.getStatus().toUpperCase()));
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Invalid status value provided: '" + dto.getStatus() + "'. Must be one of " + java.util.Arrays.toString(EvaluationStatus.values()));
            }
        }
        return evaluationRepository.save(evaluation);
    }

    @Override
    public GroupedEvaluationsResponse getGroupedEvaluations(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Evaluation> pendingPage = evaluationRepository.findByStatus(EvaluationStatus.PENDING, pageable);
        Page<Evaluation> completedPage = evaluationRepository.findByStatus(EvaluationStatus.COMPLETED, pageable);

        EvaluationPage pendingEvaluations = new EvaluationPage(pendingPage);
        EvaluationPage completedEvaluations = new EvaluationPage(completedPage);

        return new GroupedEvaluationsResponse(pendingEvaluations, completedEvaluations);
    }
}
