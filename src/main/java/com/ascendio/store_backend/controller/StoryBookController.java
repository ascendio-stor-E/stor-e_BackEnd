package com.ascendio.store_backend.controller;

import com.ascendio.store_backend.model.Story;
import com.ascendio.store_backend.model.StoryBook;
import com.ascendio.store_backend.service.StoryBookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/storybook")
public class StoryBookController {

    private StoryBookService service;

    public StoryBookController(StoryBookService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<StoryBook>> getStoryBooks(@RequestParam UUID userId){
        return ResponseEntity.ok(service.getStoryBooks(userId));
    }

    @GetMapping("/{storyBookId}")
    public ResponseEntity<Optional<StoryBook>> getStoryBookById(@PathVariable UUID storyBookId) {
        return ResponseEntity.ok(service.getStoryBookById(storyBookId));
    }

    @DeleteMapping("/{storyBookId}")
    public void deleteStoryBook (@PathVariable UUID storyBookId) {
        service.deleteStoryBook(storyBookId);
        ResponseEntity.noContent();
    }

}
