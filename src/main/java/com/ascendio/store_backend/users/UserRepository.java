package com.ascendio.store_backend.users;

import com.ascendio.store_backend.stories.StoryUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<StoryUser, UUID> {
}
