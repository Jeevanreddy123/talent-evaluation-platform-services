
package com.talentEvaluation.repository;

import com.talentEvaluation.enums.DifficultyLevel;
import com.talentEvaluation.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    @Modifying
    @Query("DELETE FROM Question q WHERE q.technology = :technology")
    void deleteByTechnology(String technology);
    List<Question> findByDifficultyLevelAndTechnology(DifficultyLevel difficultyLevel, String technology);
}
