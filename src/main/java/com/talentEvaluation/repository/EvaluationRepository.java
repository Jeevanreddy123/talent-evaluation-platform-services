package com.talentEvaluation.repository;

import com.talentEvaluation.entity.Evaluation;
import com.talentEvaluation.enums.EvaluationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {
    List<Evaluation> findByEvaluatorAssociateId(Long associateId);
    Page<Evaluation> findByStatus(EvaluationStatus status, Pageable pageable);
}

