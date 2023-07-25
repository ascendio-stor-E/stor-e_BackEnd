package com.ascendio.store_backend.repository;

import com.ascendio.store_backend.model.Story;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StoryRepository extends JpaRepository<Story, UUID> {
    List<Story> findByStoryBookId(UUID storyBookId);

    Optional<Story> findFirstByStoryBookIdAndPageNumber(UUID storyBookId, int pageNumber);

}
