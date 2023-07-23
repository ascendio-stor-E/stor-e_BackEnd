package com.ascendio.store_backend.controller;

import com.ascendio.store_backend.dto.store.StoryBookResponseDto;
import com.ascendio.store_backend.dto.store.StoryDTO;
import com.ascendio.store_backend.model.StoryBookStatus;
import com.ascendio.store_backend.service.DownloadPdfService;
import com.ascendio.store_backend.service.StoryBookService;
import com.ascendio.store_backend.service.StoryService;
import com.ascendio.store_backend.util.Converter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/storybook")
public class StoryBookController {

    private StoryBookService storyBookService;
    private StoryService storyService;
    private DownloadPdfService downloadPdfService;

    public StoryBookController(StoryBookService storyBookService, StoryService storyService, DownloadPdfService downloadPdfService) {
        this.storyBookService = storyBookService;
        this.storyService = storyService;
        this.downloadPdfService = downloadPdfService;
    }

    @GetMapping
    public ResponseEntity<List<StoryBookResponseDto>> getStoryBooks(@RequestParam UUID userId){
        return ResponseEntity.ok(Converter.toStoryBookResponseDtoList(storyBookService.getStoryBooks(userId)));
    }

    @GetMapping("/{storyBookId}")
    public ResponseEntity<StoryBookResponseDto> getStoryBookById(@PathVariable UUID storyBookId) {
        return ResponseEntity.ok(Converter
                .toStoryBookResponseDto(storyBookService
                        .getStoryBookById(storyBookId,
                        Set.of(StoryBookStatus.COMPLETE, StoryBookStatus.DRAFT, StoryBookStatus.FAVOURITE))));
    }

    @DeleteMapping("/{storyBookId}")
    public ResponseEntity<String> deleteStoryBook (@PathVariable UUID storyBookId) {
        storyBookService.deleteStoryBook(storyBookId);
        return ResponseEntity.ok("Storybook deleted successfully");
    }

    @GetMapping("/{storyBookId}/stories")
    public ResponseEntity<List<StoryDTO>> getStoriesByStoryBookId(@PathVariable UUID storyBookId) {
        return ResponseEntity.ok(Converter.storyListToDTO(storyService.getStories(storyBookId)));
    }

    @PatchMapping("/{storyBookId}/favourite")
    public ResponseEntity<Void> updateFavouriteStoryBook(@PathVariable UUID storyBookId) {
        storyBookService.updateFavouriteStoryBook(storyBookId);
        return ResponseEntity.ok().build();
    }
    @GetMapping(value = "/{storyBookId}/download", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> downloadStoryBookPdf(@PathVariable UUID storyBookId) {
        return ResponseEntity.ok(downloadPdfService.generateStoryBookPdf(storyBookId));
    }

}
