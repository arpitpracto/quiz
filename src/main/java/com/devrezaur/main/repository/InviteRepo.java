package com.devrezaur.main.repository;

import com.devrezaur.main.model.Invite;
import com.devrezaur.main.model.Test;
import com.devrezaur.main.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InviteRepo extends JpaRepository<Invite, Integer> {

    List<Invite> findByTest(Test test);

    @Query("SELECT COUNT(i) > 0 FROM Invite i WHERE i.test = :test AND i.user = :user")
    boolean existsByTestAndUser(@Param("test") Test test, @Param("user") User user);
}
