package com.example.userservice.repository.users;

import com.example.userservice.model.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {


    Optional<User> findOptionalByLogin(String login);

    @Query(value = "SELECT * FROM users WHERE first_name = :firstName AND second_name = :secondName", nativeQuery = true)
    List<User> findByName(String firstName, String secondName);
}
