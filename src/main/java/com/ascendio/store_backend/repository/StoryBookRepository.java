package com.ascendio.store_backend.repository;

import com.ascendio.store_backend.model.StoryBook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface StoryBookRepository extends JpaRepository<StoryBook, UUID> {

    List<StoryBook> findAllByStoryUserId(UUID userId);
}
