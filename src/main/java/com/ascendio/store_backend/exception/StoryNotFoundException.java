package com.ascendio.store_backend.exception;


import java.util.UUID;

public class StoryNotFoundException extends RuntimeException {
    public StoryNotFoundException(UUID storyId) {
        super("Story not found for id: %s".formatted(storyId));
    }
}
