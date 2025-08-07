
package com.talentEvaluation.dto;

import lombok.Data;

@Data
public class EvaluateCandidateDto {
    private Long candidateId;
    private Integer score;
    private String notes;
}
