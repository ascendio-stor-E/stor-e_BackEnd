package com.ascendio.store_backend.dto.store;

import java.util.List;
import java.util.UUID;

public record ContinueDraftStoryBookDto(UUID storyBookId,
                                        String conversationId,
                                        List<ContinueDraftStoryBookPageDto> pages,
                                        List<String> options) {
}
