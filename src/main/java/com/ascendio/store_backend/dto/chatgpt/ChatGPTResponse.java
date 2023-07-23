package com.ascendio.store_backend.dto.chatgpt;

import java.util.List;

public record ChatGPTResponse(String id, List<Choice> choices) {
}
