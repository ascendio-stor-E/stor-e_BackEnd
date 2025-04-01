package com.ascendio.store_backend.stories;

import com.ascendio.store_backend.storybooks.StoryBookStatus;

import java.util.UUID;

public record StoryResponseDto(UUID id,
                               String textContent,
                               Integer pageNumber,
                               String image,
                               UUID storyBookId,
                               String storyBookTitle,
                               String coverImage,
                               StoryBookStatus status) {
}
