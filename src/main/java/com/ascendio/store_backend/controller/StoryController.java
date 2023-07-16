package com.ascendio.store_backend.controller;

import com.ascendio.store_backend.service.StoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/story")
public class StoryController {

    private StoryService service;

    public StoryController(StoryService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<String> getStories() {
        return ResponseEntity.ok("Welcome to Stor-E");
    }
}
