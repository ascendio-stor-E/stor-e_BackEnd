package com.ascendio.store_backend.service;

import com.ascendio.store_backend.model.Story;
import com.ascendio.store_backend.model.StoryBook;
import com.ascendio.store_backend.repository.StoryRepository;
import org.springframework.stereotype.Service;

@Service
public class StoryService {

    private StoryRepository storyRepository;

    public StoryService(StoryRepository storyRepository) {
        this.storyRepository = storyRepository;
    }

    public Story saveStory(String storyContent, int pageNumber, String image, StoryBook storyBook) {
        Story story = new Story(storyContent, pageNumber, image, storyBook);
        return storyRepository.save(story);
    }
}
