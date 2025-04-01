package com.ascendio.store_backend;

import com.ascendio.store_backend.shared.databases.azure.AzureBlobService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/story/image")
@RequiredArgsConstructor
public class ImageController {
    private final AzureBlobService azureBlobService;

    @GetMapping(value = "/{imageName}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getImage(@PathVariable String imageName) {
        return ResponseEntity.ok(azureBlobService.getImage(imageName));
    }
}
