package com.ascendio.store_backend.controller;

import com.ascendio.store_backend.model.Favourite;
import com.ascendio.store_backend.model.StoryBook;
import com.ascendio.store_backend.service.FavouritesService;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/favourites")
public class FavouritesController {

    private final FavouritesService favouritesService;

    public FavouritesController(FavouritesService favouritesService) {
        this.favouritesService = favouritesService;
    }

    @PostMapping
    public ResponseEntity<Favourite> saveFavourite(@RequestBody StoryBook storyBook) {
        if (storyBook == null) {
            return ResponseEntity.badRequest().build();
        }
        return new ResponseEntity<>(favouritesService.saveFavourite(storyBook), HttpStatus.CREATED);
    }
}
