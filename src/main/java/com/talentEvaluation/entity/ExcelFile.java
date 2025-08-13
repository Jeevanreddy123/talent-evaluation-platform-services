
package com.talentEvaluation.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "excel_files")
@Getter
@Setter
@ToString(exclude = "file")
public class ExcelFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fileName;
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @JsonIgnore
    @JdbcTypeCode(SqlTypes.BINARY)
    private byte[] file;
    private String technology;
    private String uploadedBy;
}
