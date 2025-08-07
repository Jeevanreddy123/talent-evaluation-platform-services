package com.talentEvaluation.dto;

import lombok.Data;

@Data
public class UserDto {
    private Long associateId;
    private String firstName;
    private String lastName;
    private String role;
    private String techStack;
    private String projectRole;
    private String updatedBy;
}