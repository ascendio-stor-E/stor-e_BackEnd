package com.ascendio.store_backend.storybooks;

import com.ascendio.store_backend.stories.StoryDTO;
import com.ascendio.store_backend.chatgpt.ChatGPTService;
import com.ascendio.store_backend.shared.services.DownloadPdfService;
import com.ascendio.store_backend.stories.StoryService;
import com.ascendio.store_backend.shared.utils.Converter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/storybook")
@RequiredArgsConstructor
public class StoryBookController {

    private final StoryBookService storyBookService;
    private final StoryService storyService;
    private final DownloadPdfService downloadPdfService;
    private final ChatGPTService chatGPTService;

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

    @GetMapping("/{storyBookId}/continueDraft")
    public ResponseEntity<ContinueDraftStoryBookDto> continueDraftStoryBook(@PathVariable UUID storyBookId) {
        return ResponseEntity.ok(chatGPTService.continueDraftStoryBook(storyBookId));
    }
}
