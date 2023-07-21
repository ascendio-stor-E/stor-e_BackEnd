package com.ascendio.store_backend.service;

import com.ascendio.store_backend.model.StoryUser;
import com.ascendio.store_backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<StoryUser> findUserById(UUID userId){
        return userRepository.findById(userId);
    }
}
