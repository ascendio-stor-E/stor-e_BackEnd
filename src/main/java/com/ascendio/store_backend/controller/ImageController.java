package com.ascendio.store_backend.controller;

import com.ascendio.store_backend.service.AzureBlobService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/story/image")
public class ImageController {

    private AzureBlobService azureBlobService;

    public ImageController(AzureBlobService azureBlobService) {
        this.azureBlobService = azureBlobService;
    }

    @GetMapping(value = "/{imageName}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getImage(@PathVariable String imageName) {
        return ResponseEntity.ok(azureBlobService.getImage(imageName));
    }
}
