package com.ascendio.store_backend.controller;

import com.ascendio.store_backend.model.Favourite;
import com.ascendio.store_backend.model.StoryBook;
import com.ascendio.store_backend.service.FavouritesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/favourites")
public class FavouritesController {

    private final FavouritesService favouritesService;

    public FavouritesController(FavouritesService favouritesService) {
        this.favouritesService = favouritesService;
    }

    @PostMapping("/save")
    public ResponseEntity<Favourite> saveFavourite(/*@RequestBody StoryUser storyUser, */@RequestBody String storyBook) {
        if (storyBook == null/* || storyUser == null*/) {
            return ResponseEntity.badRequest().build();
        }
        return new ResponseEntity<>(favouritesService.saveFavourite(/*storyUser, */storyBook), HttpStatus.CREATED);
    }
}
