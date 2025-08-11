
package com.talentEvaluation.entity;

import com.talentEvaluation.enums.EvaluationStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "evaluations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"evaluator"})
public class Evaluation {

    @Id
    @Column(name = "candidate_id")
    private Long candidateId;
    @Column(name = "candidate_stack")
    private String candidateStack;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "so_id")
    private String soId;
    @Column(name = "so_role")
    private String soRole;
    @Column(name = "evaluation_date")
    private Date evaluationDate;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private EvaluationStatus status;
    @Column(name = "resume_file")
    @Lob
    @JsonIgnore
    private byte[] resumeFile;
    @Column(name = "resume_file_type")
    @JsonIgnore
    private String resumeFileType;
    @Column(name = "evaluation_details", length = 500)
    private String evaluationDetails;
    @Column(name = "evaluation_feedback", length = 500)
    private String evaluationFeedback;
    @ManyToOne
    @JoinColumn(name="associate_id", nullable=false)
    @JsonIgnore
    private User evaluator;
}
