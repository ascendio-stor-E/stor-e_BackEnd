package com.ascendio.store_backend.storybooks;

import com.ascendio.store_backend.stories.StoryContinueResponseDto;

import java.util.List;
import java.util.UUID;

public record RandomStoryResponseDto(UUID storyBookId, List<StoryContinueResponseDto> stories) {
}
