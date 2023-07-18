package com.ascendio.store_backend.controller;

import com.ascendio.store_backend.dto.StoryContinueResponseDto;
import com.ascendio.store_backend.dto.StoryStartResponseDto;
import com.ascendio.store_backend.service.ChatGPTService;
import com.ascendio.store_backend.service.StoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/story")
public class StoryController {

    private StoryService service;
    private ChatGPTService chatGPTService;

    public StoryController(StoryService service, ChatGPTService chatGPTService) {
        this.service = service;
        this.chatGPTService = chatGPTService;
    }

    @GetMapping
    public ResponseEntity<String> getStories() {
        return ResponseEntity.ok("Welcome to Stor-E");
    }

    @PostMapping()
    public ResponseEntity<StoryStartResponseDto> createInitialStory() {
        return ResponseEntity.ok(chatGPTService.startStoryBook());
    }

    @PostMapping("/continueStory")
    public ResponseEntity<StoryContinueResponseDto> createContinuesStory(@RequestParam int optionChoice, @RequestParam String conversationId) {
        return ResponseEntity.ok(chatGPTService.continueStoryBook(optionChoice,conversationId));
    }


}
