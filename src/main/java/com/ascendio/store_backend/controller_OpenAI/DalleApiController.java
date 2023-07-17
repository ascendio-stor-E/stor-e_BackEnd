package com.ascendio.store_backend.controller_OpenAI;

import com.ascendio.store_backend.dto.DalleImageResponse;
import com.ascendio.store_backend.service.DalleImageGeneratorService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/api/stor-e")
public class DalleApiController {

    private DalleImageGeneratorService service;

    public DalleApiController(DalleImageGeneratorService service) {
        this.service = service;
    }

    @GetMapping("/image")
    Mono<DalleImageResponse> generateImage(@RequestParam("story_line") String storyLine) {
        return service.generateImage(storyLine).map(response -> new DalleImageResponse(storyLine, response));
    }

}
