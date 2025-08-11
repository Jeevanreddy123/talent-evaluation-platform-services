package com.talentEvaluation.dto;

import com.talentEvaluation.entity.Evaluation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EvaluationPage {
    private List<Evaluation> evaluations;
    private int pageNumber;
    private int totalPages;
    private long totalElements;

    public EvaluationPage(Page<Evaluation> page) {
        this.evaluations = page.getContent();
        this.pageNumber = page.getNumber();
        this.totalPages = page.getTotalPages();
        this.totalElements = page.getTotalElements();
    }
}