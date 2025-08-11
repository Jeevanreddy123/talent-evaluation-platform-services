
package com.talentEvaluation.dto;

import lombok.Data;

@Data
public class EvaluationDto {
    private Long candidateId;
    private String candidateStack;
    private String firstName;
    private String lastName;
    private String soId;
    private String soRole;
    private String evaluationDate;
    private String status;
    private Long evaluatorId;
}
