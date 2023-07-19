package com.ascendio.store_backend.controller;

import com.ascendio.store_backend.dto.StoryContinueResponseDto;
import com.ascendio.store_backend.dto.StoryStartResponseDto;
import com.ascendio.store_backend.model.Story;
import com.ascendio.store_backend.service.ChatGPTService;
import com.ascendio.store_backend.service.StoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/story")
public class StoryController {

    private StoryService service;
    private ChatGPTService chatGPTService;

    public StoryController(StoryService service, ChatGPTService chatGPTService) {
        this.service = service;
        this.chatGPTService = chatGPTService;
    }



    @GetMapping("/allStories")
    public ResponseEntity<List<Story>> getStories(@RequestParam UUID storyBookid) {
        return ResponseEntity.ok(service.getStories(storyBookid));
    }


    @GetMapping("/{storyId}")
    public ResponseEntity<Story> getStoryById(@PathVariable UUID storyId) {
        return ResponseEntity.ok(service.getStoryById(storyId));
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


}
