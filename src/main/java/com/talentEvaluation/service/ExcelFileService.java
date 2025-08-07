
package com.talentEvaluation.service;

import com.talentEvaluation.entity.ExcelFile;
import org.springframework.web.multipart.MultipartFile;

public interface ExcelFileService {
    ExcelFile saveExcel(MultipartFile file, String technology, String uploadedBy);
    void deleteFileByTechnology(String technology);
}
