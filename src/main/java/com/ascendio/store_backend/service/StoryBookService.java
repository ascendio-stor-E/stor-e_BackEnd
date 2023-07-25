package com.ascendio.store_backend.service;

import com.ascendio.store_backend.exception.StoryBookNotFoundException;
import com.ascendio.store_backend.model.StoryBook;
import com.ascendio.store_backend.model.StoryBookStatus;
import com.ascendio.store_backend.model.StoryUser;
import com.ascendio.store_backend.repository.StoryBookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class StoryBookService {

    private StoryBookRepository storyBookRepository;
    private UserService userService;

    public StoryBookService(StoryBookRepository storyBookRepository, UserService userService) {
        this.storyBookRepository = storyBookRepository;
        this.userService = userService;
    }

    public StoryBook createStoryBook() {
        StoryBook storyBook = new StoryBook();
        String userId = "bc644717-5970-4e0b-88a7-35d5f0931be1";
        Optional<StoryUser> user = userService.findUserById(UUID.fromString(userId));
        storyBook.setStoryUser(user.get());
        storyBook.setStatus(StoryBookStatus.DRAFT);
        return storyBookRepository.save(storyBook);
    }

    public StoryBook getStoryBookById(UUID storyBookId, Set<StoryBookStatus> statuses) {
        Optional<StoryBook> storyBook = storyBookRepository.findByIdAndStatusIn(storyBookId,
                statuses);
        if (storyBook.isPresent()) {
            return storyBook.get();
        }
        throw new StoryBookNotFoundException(storyBookId);
    }

    // get all storybooks (DRAFT, COMPLETE, FAVOURITES) at one backend call from frontend)
    public List<StoryBook> getStoryBooks(UUID userId) {
       return storyBookRepository.findAllByStoryUserIdAndStatusIsNotOrderByLastModifiedDateDesc(userId, StoryBookStatus.DELETED);
    }

    public StoryBook updateStoryBook(StoryBook storyBook) {
        return storyBookRepository.save(storyBook);
    }

    public void updateFavouriteStoryBook(UUID storyBookId) {
        storyBookRepository.findById(storyBookId).ifPresent(storyBook -> {
            storyBook.setStatus(
                    storyBook.getStatus().equals(StoryBookStatus.COMPLETE) ? StoryBookStatus.FAVOURITE : StoryBookStatus.COMPLETE);
            storyBookRepository.save(storyBook);
        });
    }

    public void deleteStoryBook(UUID storyBookId) {
        storyBookRepository.findById(storyBookId).ifPresent(storyBook -> {
            storyBook.setStatus(StoryBookStatus.DELETED);
            storyBookRepository.save(storyBook);
        });
    }
}
