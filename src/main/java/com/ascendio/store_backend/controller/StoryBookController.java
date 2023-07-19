package com.ascendio.store_backend.controller;

import com.ascendio.store_backend.service.StoryBookService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/storybook")
public class StoryBookController {

    private StoryBookService service;

    public StoryBookController(StoryBookService service) {
        this.service = service;
    }


}
