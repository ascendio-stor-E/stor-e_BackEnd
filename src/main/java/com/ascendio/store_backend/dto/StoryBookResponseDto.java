package com.ascendio.store_backend.dto;

import java.util.UUID;

public record StoryBookResponseDto(UUID id,
                                   String title,
                                   String coverImage,
                                   Boolean status,
                                   UUID storyUserId,
                                   String storyUserName,
                                   String storyUserEmail
                            ) {
}
