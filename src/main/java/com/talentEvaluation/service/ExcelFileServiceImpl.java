
package com.talentEvaluation.service;

import com.talentEvaluation.entity.ExcelFile;
import com.talentEvaluation.repository.ExcelFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ExcelFileServiceImpl implements ExcelFileService {

    @Autowired
    private ExcelFileRepository excelFileRepository;

    @Override
    public ExcelFile saveExcel(MultipartFile file, String technology, String uploadedBy) {
        try {
            ExcelFile excelFile = new ExcelFile();
            excelFile.setFileName(file.getOriginalFilename());
            excelFile.setFile(file.getBytes());
            excelFile.setTechnology(technology);
            excelFile.setUploadedBy(uploadedBy);
            return excelFileRepository.save(excelFile);
        } catch (IOException e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public void deleteFileByTechnology(String technology) {
        excelFileRepository.deleteByTechnology(technology);
    }
}
