package com.talentEvaluation.dto;

import lombok.Data;

@Data
public class UserResponse {
    private Long associateId;
    private String username;
    private String firstName;
    private String lastName;
    private String role;
    private String techStack;
    private String projectRole;
    private String updatedBy;
    private int pendingEvaluations;
    private int completedEvaluations;
}