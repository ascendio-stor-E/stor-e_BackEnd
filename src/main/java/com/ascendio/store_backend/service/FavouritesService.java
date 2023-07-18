package com.ascendio.store_backend.service;

import com.ascendio.store_backend.model.Favourite;
import com.ascendio.store_backend.model.StoryBook;
import com.ascendio.store_backend.model.StoryUser;
import com.ascendio.store_backend.repository.FavouritesRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class FavouritesService {

    private FavouritesRepository favRepo;
    private StoryBookService storyBookService;
    private UserService userService;

    public FavouritesService(FavouritesRepository favRepo, StoryBookService storyBookService, UserService userService) {
        this.favRepo = favRepo;
        this.storyBookService = storyBookService;
        this.userService = userService;
    }

    public Favourite saveFavourite(StoryBook storyBook) {
        Favourite favourite = new Favourite();

        String userId = "bc644717-5970-4e0b-88a7-35d5f0931be1";
        Optional<StoryUser> user = userService.findUserById(UUID.fromString(userId));
        favourite.setStoryUser(user.get());

        String storyBookId = String.valueOf(storyBook.getId());
        favourite.getStoryBookId().get(Integer.parseInt(storyBookId));

        return favourite;

    }
}
