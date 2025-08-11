
package com.talentEvaluation.dto;

import lombok.Data;

@Data
public class EvaluateCandidateDto {
    private Long candidateId;
    private String evaluationDetails;
    private String evaluationFeedback;
    private String status;
}
