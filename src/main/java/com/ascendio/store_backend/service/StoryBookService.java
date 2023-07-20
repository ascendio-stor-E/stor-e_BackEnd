package com.ascendio.store_backend.service;

import com.ascendio.store_backend.model.StoryBook;
import com.ascendio.store_backend.model.StoryUser;
import com.ascendio.store_backend.repository.StoryBookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StoryBookService {

    private StoryBookRepository storyBookRepository;
    private UserService userService;

    public StoryBookService(StoryBookRepository storyBookRepository, UserService userService) {
        this.storyBookRepository = storyBookRepository;
        this.userService = userService;
    }

    public StoryBook saveStoryBook() {
        StoryBook storyBook = new StoryBook();
        String userId = "bc644717-5970-4e0b-88a7-35d5f0931be1";
        Optional<StoryUser> user = userService.findUserById(UUID.fromString(userId));
        storyBook.setStoryUser(user.get());
        return storyBookRepository.save(storyBook);
    }

    public Optional<StoryBook> getStoryBookById(UUID storyBookId) {
        return storyBookRepository.findById(storyBookId);
    }

    public List<StoryBook> getStoryBooks(UUID userId) {
        return storyBookRepository.findAllByStoryUserId(userId);
    }

    public StoryBook updateStoryBook(StoryBook storyBook) {
        return storyBookRepository.save(storyBook);
    }
}
