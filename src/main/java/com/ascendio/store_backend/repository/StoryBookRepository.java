package com.ascendio.store_backend.repository;

import com.ascendio.store_backend.model.StoryBook;
import com.ascendio.store_backend.model.StoryBookStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface StoryBookRepository extends JpaRepository<StoryBook, UUID> {

    List<StoryBook> findAllByStoryUserIdAndStatusIsNotOrderByLastModifiedDateDesc(UUID userId, StoryBookStatus status);

    Optional<StoryBook> findByIdAndStatusIn(UUID storyBookId, Set<StoryBookStatus> storyBookStatuses);
}
