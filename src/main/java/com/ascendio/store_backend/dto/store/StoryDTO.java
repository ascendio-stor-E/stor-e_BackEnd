package com.ascendio.store_backend.dto.store;

import java.util.UUID;

public record StoryDTO(UUID id, String textContent, int pageNumber, String image) {}

