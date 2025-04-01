package com.ascendio.store_backend.chatgpt;

import java.util.List;

public record ChatGPTResponse(String id, List<Choice> choices) {
}
