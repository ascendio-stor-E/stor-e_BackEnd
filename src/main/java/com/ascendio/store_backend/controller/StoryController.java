package com.ascendio.store_backend.controller;

import com.ascendio.store_backend.dto.*;
import com.ascendio.store_backend.service.ChatGPTService;
import com.ascendio.store_backend.service.StoryService;
import com.ascendio.store_backend.util.Converter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/story")
public class StoryController {
    private final StoryService storyService;
    private final ChatGPTService chatGPTService;

    public StoryController(StoryService storyService, ChatGPTService chatGPTService) {
        this.storyService = storyService;
        this.chatGPTService = chatGPTService;
    }

    @PostMapping()
    public ResponseEntity<StoryStartResponseDto> createInitialStory() {
        return ResponseEntity.ok(chatGPTService.startStoryBook());
    }

    @PostMapping("/continueStory")
    public ResponseEntity<StoryContinueResponseDto> createContinuesStory(@RequestParam int optionChoice,
                                                                         @RequestParam String conversationId,
                                                                         @RequestParam UUID storyBookId,
                                                                         @RequestParam int pageNumber) {
        return ResponseEntity.ok(chatGPTService.continueStoryBook(optionChoice,conversationId,storyBookId,pageNumber));
    }

    @PostMapping("/randomStory")
    public ResponseEntity<RandomStoryResponseDto> createRandomStory(@RequestParam String option,
                                                                    @RequestParam UUID storyBookId) {
        return ResponseEntity.ok(chatGPTService.createRandomStory(option,storyBookId));
    }

    @GetMapping("/{storyId}")
    public ResponseEntity<Optional<StoryResponseDto>> getStoryById(@PathVariable UUID storyId) {
        return ResponseEntity.ok(storyService.getStoryById(storyId).map(story ->
                Converter.toStoryResponseDto(story)));
    }
}
