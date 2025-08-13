
package com.talentEvaluation.repository;

import com.talentEvaluation.entity.ExcelFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ExcelFileRepository extends JpaRepository<ExcelFile, Long> {
    @Modifying
    @Query("DELETE FROM ExcelFile ef WHERE ef.technology = :technology")
    void deleteByTechnology(String technology);
}
