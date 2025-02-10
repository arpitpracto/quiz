package com.devrezaur.main.repository;

import com.devrezaur.main.model.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TestRepo extends JpaRepository<Test, Integer> {
    //List<Test> findTestsByQuestionId(Integer questionId);
    Optional<Test> findByTestName(String testName);


}
