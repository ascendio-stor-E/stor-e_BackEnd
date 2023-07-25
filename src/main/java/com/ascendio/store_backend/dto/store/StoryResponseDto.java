package com.ascendio.store_backend.dto.store;

import com.ascendio.store_backend.model.StoryBookStatus;

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
