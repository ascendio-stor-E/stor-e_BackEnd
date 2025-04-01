package com.ascendio.store_backend.stories;

import java.util.UUID;

public record StoryDTO(UUID id, String textContent, int pageNumber, String image) {}

