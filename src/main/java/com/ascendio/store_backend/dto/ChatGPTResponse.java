package com.ascendio.store_backend.dto;

import java.util.Date;
import java.util.List;

public record ChatGPTResponse(String id, Long created, List<Choice> choices) {
}
