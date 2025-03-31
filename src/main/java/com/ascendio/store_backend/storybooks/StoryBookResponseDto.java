package com.ascendio.store_backend.storybooks;

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
