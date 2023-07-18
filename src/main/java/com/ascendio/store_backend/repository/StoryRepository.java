package com.ascendio.store_backend.repository;

import com.ascendio.store_backend.model.Story;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StoryRepository extends JpaRepository<Story, UUID> {

}
