package com.ascendio.store_backend.dto;

import java.util.List;
import java.util.UUID;

public record RandomStoryResponseDto(UUID storyBookId, List<StoryContinueResponseDto> stories) {
}
