package com.ascendio.store_backend.chatgpt;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ChatGPTRequest(String model, List<ChatGPTMessage> messages, int n, double temperature) {
}
