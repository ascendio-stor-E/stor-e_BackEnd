package com.ascendio.store_backend.dto;

import java.util.List;
import java.util.UUID;

public record StoryStartResponseDto(List<String> options, String conversationId, UUID storyBookId) {
}
