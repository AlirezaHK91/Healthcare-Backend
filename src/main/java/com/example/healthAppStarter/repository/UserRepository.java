package com.example.healthAppStarter.repository;

import com.example.healthAppStarter.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    User findByEmail(String email);

    User findUserById(Long id);
}
