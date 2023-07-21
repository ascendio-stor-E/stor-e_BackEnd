package com.ascendio.store_backend.repository;

import com.ascendio.store_backend.model.StoryUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<StoryUser, UUID> {
}
