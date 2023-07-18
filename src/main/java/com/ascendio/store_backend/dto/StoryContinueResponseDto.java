package com.ascendio.store_backend.dto;

import java.util.List;

public record StoryContinueResponseDto(String part, String story, List<String> options) {
}
