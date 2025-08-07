
package com.talentEvaluation.repository;

import com.talentEvaluation.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    void deleteByTechnology(String technology);
    List<Question> findByDifficultyLevelAndTechnology(String difficultyLevel, String technology);
}
