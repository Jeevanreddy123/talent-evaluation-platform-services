
package com.talentEvaluation.entity;

import com.talentEvaluation.enums.DifficultyLevel;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "questions")
@Data
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String questionText;
    @Enumerated(EnumType.STRING)
    private DifficultyLevel difficultyLevel;
    private String technology;
    private String answer;
}
