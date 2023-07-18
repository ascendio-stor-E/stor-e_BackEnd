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

    private FavouritesRepository favRepo;
    private StoryBookService storyBookService;
    private UserService userService;

    public FavouritesService(FavouritesRepository favRepo, StoryBookService storyBookService, UserService userService) {
        this.favRepo = favRepo;
        this.storyBookService = storyBookService;
        this.userService = userService;
    }

    public Favourite saveFavourite(/*String userId,*/ String storyBookId) {
        Favourite favourite = new Favourite();

        String userId = "bc644717-5970-4e0b-88a7-35d5f0931be1";
        Optional<StoryUser> user = userService.findUserById(UUID.fromString(userId));


        String bookId = String.valueOf(storyBookId);
        favourite.getStoryBookId().get(Integer.parseInt(bookId));

        return favRepo.save(favourite);
    }

    public List<Favourite> getAllUserFavourites(/*String userId*/) {
        String userId = "bc644717-5970-4e0b-88a7-35d5f0931be1";
        return null;
    }
}
