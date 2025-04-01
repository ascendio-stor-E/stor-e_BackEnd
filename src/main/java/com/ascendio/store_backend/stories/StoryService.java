package com.ascendio.store_backend.stories;

import com.ascendio.store_backend.storybooks.StoryBook;
import com.ascendio.store_backend.storybooks.StoryBookStatus;
import com.ascendio.store_backend.storybooks.StoryBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StoryService {
    private final StoryRepository storyRepository;
    private final StoryBookService.StoryBookRepository storyBookRepository;

    private static final Integer MAX_NUMBER_OF_STORIES = 5;

    public Story saveStory(String storyContent, int pageNumber, String imageName, StoryBook storyBook) {
        if (pageNumber == MAX_NUMBER_OF_STORIES) {
            storyBook.setStatus(StoryBookStatus.COMPLETE);
        }
        Story story = new Story(storyContent, pageNumber, imageName, storyBook);
        return storyRepository.save(story);
    }

    public void updateStoryImage(Story story, String imageName){
        story.setImage(imageName);
        storyRepository.save(story);
    }

    public List<Story> getStories(UUID storyBookId) {
        return storyBookRepository.findById(storyBookId)
                .map(StoryBook::getStories).orElseGet(ArrayList::new);
    }

    public Optional<Story> getStoryById(UUID storyId) {
        return storyRepository.findById(storyId);
    }

    public Optional<Story> getStoryByBookIdAndPageNumber(UUID storyBookId, int pageNumber) {
        return storyRepository.findFirstByStoryBookIdAndPageNumber(storyBookId, pageNumber);
    }
}
