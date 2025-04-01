package com.ascendio.store_backend.dalle;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/* TODO: remove this controller which is used to test Dall-E API */

@RestController
@RequestMapping("/api/storE")
@RequiredArgsConstructor
public class DalleApiController {

    private final DalleImageGeneratorService service;

    @GetMapping("/image")
    public ResponseEntity<DalleImageResponse> generateImage(@RequestParam("storyText") String storyText) {
        return ResponseEntity.ok(new DalleImageResponse(storyText, service.generateImage(storyText)));
    }
}
