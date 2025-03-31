package com.ascendio.store_backend.shared.exceptions;

import java.util.UUID;

public class StoryBookNotFoundException extends RuntimeException{
    public StoryBookNotFoundException(UUID storyBookId) {
        super("StoryBook not found for storyBookId : " + storyBookId);
    }
}
