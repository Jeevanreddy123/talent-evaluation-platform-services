
package com.talentEvaluation.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "evaluations")
@Data
public class Evaluation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String candidateName;
    private String skill;
    private Integer score;
    private Date evaluationDate;
    private String notes;
    @Lob
    private byte[] resume;

    @ManyToOne
    @JoinColumn(name = "evaluator_id")
    private User evaluator;
}
