package com.ascendio.store_backend.service;

import com.ascendio.store_backend.model.Favourite;
import com.ascendio.store_backend.model.StoryBook;
import com.ascendio.store_backend.model.StoryUser;
import com.ascendio.store_backend.repository.FavouritesRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FavouritesService {

    private final FavouritesRepository favRepo;
    private final StoryBookService storyBookService;
    private final UserService userService;

    public FavouritesService(FavouritesRepository favRepo, StoryBookService storyBookService, UserService userService) {
        this.favRepo = favRepo;
        this.storyBookService = storyBookService;
        this.userService = userService;
    }

    public Favourite saveFavourite(UUID storyBookId) {
        Favourite favourite = new Favourite();

        String userId = "bc644717-5970-4e0b-88a7-35d5f0931be1";
        Optional<StoryUser> user = userService.findUserById(UUID.fromString(userId));
        favourite.setStoryUser(user.get());

        Optional<StoryBook> book = storyBookService.findStoryBookById(storyBookId);
        favourite.setStoryBookId((List<StoryBook>) book.get());

        return favRepo.save(favourite);
    }

    public List<Favourite> getAllUserFavourites() {
        return (List<Favourite>) favRepo.findAll();
    }

    public void deleteFavourite(UUID storyBookId) {
        favRepo.deleteById(storyBookId);
    }
}
