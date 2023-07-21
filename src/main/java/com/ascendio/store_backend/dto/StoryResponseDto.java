package com.ascendio.store_backend.dto;

import java.util.UUID;

public record StoryResponseDto(UUID id,
                               String textContent,
                               Integer pageNumber,
                               String image,
                               UUID storyBookId,
                               String storyBookTitle,
                               String coverImage,
                               Boolean status) {
}
