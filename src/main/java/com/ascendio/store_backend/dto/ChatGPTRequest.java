package com.ascendio.store_backend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ChatGPTRequest(String model, List<ChatGPTMessage> messages, int n, double temperature, @JsonProperty("conversation_id") String conversationId) {
}
