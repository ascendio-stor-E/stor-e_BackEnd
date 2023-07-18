package com.ascendio.store_backend.repository;

import com.ascendio.store_backend.dto.ChatGPTMessage;
import com.ascendio.store_backend.model.ChatGPTHistory;
import com.ascendio.store_backend.model.Story;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface StoryRepository extends JpaRepository<Story, UUID> {

}
