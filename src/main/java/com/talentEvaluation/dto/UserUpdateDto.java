package com.talentEvaluation.dto;

import lombok.Data;

@Data
public class UserUpdateDto {
    private Long associateId;
    private String role;
    private String techStack;
    private String projectRole;
    private String updatedBy;
}