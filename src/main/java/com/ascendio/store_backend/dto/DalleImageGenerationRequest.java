package com.ascendio.store_backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DalleImageGenerationRequest(@JsonProperty("prompt") String storyText,
                                          @JsonProperty("size") String imageSize,
                                          @JsonProperty("n") Integer numberOfImages) {
}
