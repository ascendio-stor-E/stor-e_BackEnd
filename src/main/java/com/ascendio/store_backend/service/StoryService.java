package com.ascendio.store_backend.service;

import com.ascendio.store_backend.dto.StoriesDto;
import com.ascendio.store_backend.model.Story;
import com.ascendio.store_backend.model.StoryBook;
import com.ascendio.store_backend.repository.StoryBookRepository;
import com.ascendio.store_backend.repository.StoryRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

@Service
public class StoryService {

    private StoryRepository storyRepository;
    private StoryBookRepository storyBookRepository;

    public StoryService(StoryRepository storyRepository, StoryBookRepository storyBookRepository) {
        this.storyRepository = storyRepository;
        this.storyBookRepository = storyBookRepository;
    }

    public Story saveStory(String storyContent, int pageNumber, String imageName, StoryBook storyBook) {
        Story story = new Story(storyContent, pageNumber, imageName, storyBook);
        return storyRepository.save(story);
    }

    public List<Story> getStories(UUID storyBookid) {
        return storyRepository.findAllByStoryBookId(storyBookid);
    }

    public Story getStoryById(UUID storyId) {
        return null;
    }
}
