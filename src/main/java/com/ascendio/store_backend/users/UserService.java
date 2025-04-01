package com.ascendio.store_backend.users;

import com.ascendio.store_backend.stories.StoryUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Optional<StoryUser> findUserById(UUID userId){
        return userRepository.findById(userId);
    }
}
