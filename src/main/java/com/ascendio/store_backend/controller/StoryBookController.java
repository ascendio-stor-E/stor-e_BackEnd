package com.ascendio.store_backend.controller;

import com.ascendio.store_backend.dto.StoryBookResponseDto;
import com.ascendio.store_backend.service.StoryBookService;
import com.ascendio.store_backend.util.Converter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/storybook")
public class StoryBookController {

    private StoryBookService storyBookService;

    public StoryBookController(StoryBookService storyBookService) {
        this.storyBookService = storyBookService;
    }

    @GetMapping
    public ResponseEntity<List<StoryBookResponseDto>> getStoryBooks(@RequestParam UUID userId){
        return ResponseEntity.ok(Converter.toStoryBookResponseDtoList(storyBookService.getStoryBooks(userId)));
    }

    @GetMapping("/{storyBookId}")
    public ResponseEntity<StoryBookResponseDto> getStoryBookById(@PathVariable UUID storyBookId) {
        return ResponseEntity.ok(Converter.toStoryBookResponseDto(storyBookService.getStoryBookById(storyBookId)));
    }

    @DeleteMapping("/{storyBookId}")
    public void deleteStoryBook (@PathVariable UUID storyBookId) {
        storyBookService.deleteStoryBook(storyBookId);
        ResponseEntity.noContent();
    }

}
