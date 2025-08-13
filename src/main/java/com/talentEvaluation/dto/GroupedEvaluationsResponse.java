package com.talentEvaluation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GroupedEvaluationsResponse {
    private final EvaluationPage<?> pending;
    private final EvaluationPage<?> completed;
}

