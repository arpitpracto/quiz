package com.devrezaur.main.repository;

import com.devrezaur.main.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepo extends JpaRepository<Question, Integer> {
    @Query("SELECT q FROM Question q JOIN q.tests t WHERE t.testId = :testId")
    List<Question> findQuestionsByTestId(@Param("testId") Integer testId);
}

