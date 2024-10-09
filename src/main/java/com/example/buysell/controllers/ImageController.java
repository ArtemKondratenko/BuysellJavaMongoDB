package com.example.buysell.controllers;

import com.example.buysell.models.Image;
import com.example.buysell.repositories.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ImageController {
    private final ImageRepository imageRepository;

    @GetMapping("/images/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable String id) {
        Image image = imageRepository.findById(id).orElse(null);
        if (image != null) {
            return ResponseEntity.ok()
                    .contentType(MediaType.valueOf(image.getContentType()))
                    .body(image.getBytes());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}