package com.devrezaur.main.repository;

import com.devrezaur.main.model.Response;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResponseRepo extends JpaRepository<Response, Integer> {
}
