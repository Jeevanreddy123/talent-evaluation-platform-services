package com.talentEvaluation.repository;

import com.talentEvaluation.entity.Evaluation;
import com.talentEvaluation.enums.EvaluationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {
    List<Evaluation> findByEvaluatorAssociateId(Long associateId);
    Page<Evaluation> findByStatus(EvaluationStatus status, Pageable pageable);

    @Query("SELECT e FROM Evaluation e WHERE e.status = :status AND (LOWER(e.firstName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR LOWER(e.lastName) LIKE LOWER(CONCAT('%', :searchTerm, '%')))")
    Page<Evaluation> findByStatusAndSearchTerm(@Param("status") EvaluationStatus status, @Param("searchTerm") String searchTerm, Pageable pageable);

    Page<Evaluation> findByEvaluatorAssociateIdAndStatus(Long associateId, EvaluationStatus status, Pageable pageable);
}

