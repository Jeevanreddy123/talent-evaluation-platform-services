package com.talentEvaluation.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class CandidateAlreadyAssignedException extends RuntimeException {
    public CandidateAlreadyAssignedException(String message) {
        super(message);
    }
}