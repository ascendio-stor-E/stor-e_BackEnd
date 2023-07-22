package com.ascendio.store_backend.dto;

import com.ascendio.store_backend.model.StoryBookStatus;

import java.util.UUID;

public record StoryBookResponseDto(UUID id,
                                   String title,
                                   String coverImage,
                                   StoryBookStatus status,
                                   UUID storyUserId,
                                   String storyUserName,
                                   String storyUserEmail
                            ) {
}
