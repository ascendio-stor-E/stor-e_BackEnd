package com.ascendio.store_backend.dto;

import java.util.List;
import java.util.UUID;

public record StoryContinueResponseDto(String part, String story, List<String> options, String imageName, UUID storyId) {
}
