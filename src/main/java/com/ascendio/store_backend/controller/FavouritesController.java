package com.ascendio.store_backend.controller;

import com.ascendio.store_backend.model.Favourite;
import com.ascendio.store_backend.service.FavouritesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/favourites")
public class FavouritesController {

    private final FavouritesService favouritesService;

    public FavouritesController(FavouritesService favouritesService) {
        this.favouritesService = favouritesService;
    }

    @PostMapping("/save")
    public ResponseEntity<Favourite> saveFavourite(@RequestBody UUID storyBookId) {
        return ResponseEntity.ok(favouritesService.saveFavourite(storyBookId));
    }

    @GetMapping
    public ResponseEntity<List<Favourite>> getFavourites() {
        List<Favourite> favourites = favouritesService.getAllUserFavourites();
        return ResponseEntity.ok(favourites);
    }

    @DeleteMapping("/{storyBookId}")
    public ResponseEntity<Void> deleteFavourite(@PathVariable UUID storyBookId){
        favouritesService.deleteFavourite(storyBookId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
