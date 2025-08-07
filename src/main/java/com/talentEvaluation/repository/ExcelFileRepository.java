
package com.talentEvaluation.repository;

import com.talentEvaluation.entity.ExcelFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExcelFileRepository extends JpaRepository<ExcelFile, Long> {
    void deleteByTechnology(String technology);
}
