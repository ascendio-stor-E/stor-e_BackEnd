package com.ascendio.store_backend.dto;

import java.util.List;

public record StoryStartResponseDto(List<String> options, String conversationId) {
}
