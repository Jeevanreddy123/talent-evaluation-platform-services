
package com.talentEvaluation.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "excel_files")
@Data
public class ExcelFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fileName;
    @Lob
    private byte[] file;
    private String technology;
    private String uploadedBy;
}
