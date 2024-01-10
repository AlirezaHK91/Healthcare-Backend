package com.example.healthAppStarter.Services;

import com.example.healthAppStarter.models.User;
import com.example.healthAppStarter.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User findUserById(Long id) {
        return  userRepository.findUserById(id);
    }

}
