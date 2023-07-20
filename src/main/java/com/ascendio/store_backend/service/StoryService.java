package com.ascendio.store_backend.service;

import com.ascendio.store_backend.model.Story;
import com.ascendio.store_backend.model.StoryBook;
import com.ascendio.store_backend.repository.StoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StoryService {

    private static final Integer MAX_NUMBER_OF_STORIES = 5;
    private final StoryRepository storyRepository;

    public StoryService(StoryRepository storyRepository) {
        this.storyRepository = storyRepository;
    }

    public Story saveStory(String storyContent, int pageNumber, String imageName, StoryBook storyBook) {
        if (pageNumber == MAX_NUMBER_OF_STORIES) {
            storyBook.setStatus(true);
        }
        Story story = new Story(storyContent, pageNumber, imageName, storyBook);
        return storyRepository.save(story);
    }

    public List<Story> getStories(UUID storyBookId) {
        return storyRepository.findByStoryBookId(storyBookId);
    }

    public Optional<Story> getStoryById(UUID storyId) {
        return storyRepository.findById(storyId);
    }
}
