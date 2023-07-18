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
    public ResponseEntity<Favourite> saveFavourite(/*@RequestBody StoryUser storyUser, */@RequestBody String storyBookId) {
        return ResponseEntity.ok(favouritesService.saveFavourite(/*storyUser, */storyBookId));
    }
}
