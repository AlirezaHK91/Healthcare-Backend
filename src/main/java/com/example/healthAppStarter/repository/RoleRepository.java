package com.example.healthAppStarter.repository;

import com.example.healthAppStarter.models.ERole;
import com.example.healthAppStarter.models.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RoleRepository extends MongoRepository<Role, String> {
    Optional<Role> findByName(ERole name);
}
