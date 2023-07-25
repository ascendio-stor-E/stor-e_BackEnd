package com.ascendio.store_backend.dto.store;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;
import java.util.UUID;

public record ContinueDraftStoryBookPageDto(String part,
                                            String story,
                                            List<String> options,
                                            String imageName,
                                            UUID storyId,
                                            @JsonIgnore
                                            String conversationId) {
}
