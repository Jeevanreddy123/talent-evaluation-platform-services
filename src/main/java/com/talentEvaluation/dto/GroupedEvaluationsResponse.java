package com.talentEvaluation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupedEvaluationsResponse {
    private EvaluationPage pending;
    private EvaluationPage completed;
}