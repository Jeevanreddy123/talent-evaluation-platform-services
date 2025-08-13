package com.talentEvaluation.entity;

import com.talentEvaluation.enums.EvaluationStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "evaluations")
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

    @Temporal(TemporalType.DATE)
    @Column(name = "evaluation_date")
    private Date evaluationDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private EvaluationStatus status;

    /**
     * The resume file is stored as a byte array.
     * FetchType.LAZY is crucial here. It prevents Hibernate from loading this
     * potentially large field unless it's explicitly accessed via getResumeFile().
     * This resolves the "getBlob not implemented by SQLite JDBC driver" error
     * and improves performance for list-based queries.
     */
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @JsonIgnore
    @Column(name = "resume_file")
    @JdbcTypeCode(SqlTypes.BINARY)
    private byte[] resumeFile;

    @Column(name = "resume_file_type")
    private String resumeFileType;

    @Column(name = "evaluation_details")
    private String evaluationDetails;

    @Column(name = "evaluation_feedback")
    private String evaluationFeedback;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "associate_id", nullable = false)
    private User evaluator;

    // Getters and Setters

    public Long getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(Long candidateId) {
        this.candidateId = candidateId;
    }

    public String getCandidateStack() {
        return candidateStack;
    }

    public void setCandidateStack(String candidateStack) {
        this.candidateStack = candidateStack;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSoId() {
        return soId;
    }

    public void setSoId(String soId) {
        this.soId = soId;
    }

    public String getSoRole() {
        return soRole;
    }

    public void setSoRole(String soRole) {
        this.soRole = soRole;
    }

    public Date getEvaluationDate() {
        return evaluationDate;
    }

    public void setEvaluationDate(Date evaluationDate) {
        this.evaluationDate = evaluationDate;
    }

    public EvaluationStatus getStatus() {
        return status;
    }

    public void setStatus(EvaluationStatus status) {
        this.status = status;
    }

    public byte[] getResumeFile() {
        return resumeFile;
    }

    public void setResumeFile(byte[] resumeFile) {
        this.resumeFile = resumeFile;
    }

    public String getResumeFileType() {
        return resumeFileType;
    }

    public void setResumeFileType(String resumeFileType) {
        this.resumeFileType = resumeFileType;
    }

    public String getEvaluationDetails() {
        return evaluationDetails;
    }

    public void setEvaluationDetails(String evaluationDetails) {
        this.evaluationDetails = evaluationDetails;
    }

    public String getEvaluationFeedback() {
        return evaluationFeedback;
    }

    public void setEvaluationFeedback(String evaluationFeedback) {
        this.evaluationFeedback = evaluationFeedback;
    }

    public User getEvaluator() {
        return evaluator;
    }

    public void setEvaluator(User evaluator) {
        this.evaluator = evaluator;
    }
}
