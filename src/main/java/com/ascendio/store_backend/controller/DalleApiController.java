package com.ascendio.store_backend.controller;

import com.ascendio.store_backend.dto.dalle.DalleImageResponse;
import com.ascendio.store_backend.service.DalleImageGeneratorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/* TODO: remove this controller which is used to test Dall-E API */

@RestController
@RequestMapping("/api/storE")
public class DalleApiController {

    private DalleImageGeneratorService service;

    public DalleApiController(DalleImageGeneratorService service) {
        this.service = service;
    }

    @GetMapping("/image")
    public ResponseEntity<DalleImageResponse> generateImage(@RequestParam("storyText") String storyText) {
        return ResponseEntity.ok(new DalleImageResponse(storyText, service.generateImage(storyText)));
    }

}
