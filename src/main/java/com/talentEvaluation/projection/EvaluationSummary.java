package com.talentEvaluation.projection;

import com.talentEvaluation.entity.User;
import com.talentEvaluation.enums.EvaluationStatus;

import java.util.Date;

// This is a projection interface. Spring Data JPA will automatically
// implement it and only select the columns corresponding to these getter methods.
// Crucially, it excludes the 'resumeFile' byte array.
public interface EvaluationSummary {
    Long getCandidateId();
    String getCandidateStack();
    String getFirstName();
    String getLastName();
    String getSoId();
    String getSoRole();
    Date getEvaluationDate();
    EvaluationStatus getStatus();
    String getEvaluationDetails();
    String getEvaluationFeedback();
    User getEvaluator();
}