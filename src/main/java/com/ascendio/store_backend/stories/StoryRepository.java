package com.ascendio.store_backend.stories;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface StoryRepository extends JpaRepository<Story, UUID> {
    Optional<Story> findFirstByStoryBookIdAndPageNumber(UUID storyBookId, int pageNumber);

}
