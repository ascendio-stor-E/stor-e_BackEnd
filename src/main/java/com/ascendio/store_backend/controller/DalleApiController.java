package com.ascendio.store_backend.controller;

import com.ascendio.store_backend.dto.DalleImageResponse;
import com.ascendio.store_backend.service.DalleImageGeneratorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
// Possibly review this endpoint name? Similar to StoryController's.
@RequestMapping("/api/storE")
public class DalleApiController {

    private DalleImageGeneratorService service;

    public DalleApiController(DalleImageGeneratorService service) {
        this.service = service;
    }

    @GetMapping("/image")
    public ResponseEntity<DalleImageResponse> generateImage(@RequestParam("storyLine") String storyLine) {
        return ResponseEntity.ok(new DalleImageResponse(storyLine, service.generateImage(storyLine)));
    }

}
