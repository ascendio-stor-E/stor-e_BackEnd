package com.ascendio.store_backend.stories;

import com.ascendio.store_backend.storybooks.RandomStoryResponseDto;
import com.ascendio.store_backend.chatgpt.ChatGPTService;
import com.ascendio.store_backend.shared.utils.Converter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/story")
@RequiredArgsConstructor
public class StoryController {
    private final StoryService storyService;
    private final ChatGPTService chatGPTService;

    @PostMapping()
    public ResponseEntity<StoryStartResponseDto> createInitialStory(@RequestParam String characterName) {
        return ResponseEntity.ok(chatGPTService.startStoryBook(characterName));
    }

    @PostMapping("/continueStory")
    public ResponseEntity<StoryContinueResponseDto> createContinuesStory(
             @RequestParam int optionChoice,
             @RequestParam String conversationId,
             @RequestParam UUID storyBookId,
             @RequestParam int pageNumber) {
        return ResponseEntity.ok(chatGPTService.continueStoryBook(optionChoice,conversationId,storyBookId,pageNumber));
    }

    @PostMapping("/randomStory")
    public ResponseEntity<RandomStoryResponseDto> createRandomStory(
            @RequestParam String option,
            @RequestParam UUID storyBookId) {
        return ResponseEntity.ok(chatGPTService.createRandomStory(option,storyBookId));
    }

    @GetMapping("/{storyId}")
    public ResponseEntity<Optional<StoryResponseDto>> getStoryById(@PathVariable UUID storyId) {
        return ResponseEntity.ok(storyService.getStoryById(storyId).map(Converter::toStoryResponseDto));
    }
}
