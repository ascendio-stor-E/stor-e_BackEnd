package com.ascendio.store_backend.dto.store;

import com.ascendio.store_backend.model.StoryBookStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record StoryBookResponseDto(UUID id,
                                   String title,
                                   String coverImage,
                                   StoryBookStatus status,
                                   Integer numberOfPages,
                                   LocalDateTime lastModifiedDate
                            ) {
}
