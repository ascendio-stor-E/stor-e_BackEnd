package com.ascendio.store_backend.dto;

import com.ascendio.store_backend.model.Story;

import java.util.List;
import java.util.UUID;

public record StoriesDto(UUID storyBookId, List<Story> stories) {
}
