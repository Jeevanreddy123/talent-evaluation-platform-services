
package com.talentEvaluation.dto;

import lombok.Data;

@Data
public class EvaluationDto {
    private String candidateName;
    private String skill;
    private String evaluationDate;
    private Long evaluatorId;
}
