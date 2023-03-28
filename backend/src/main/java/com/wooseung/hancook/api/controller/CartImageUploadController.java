package com.wooseung.hancook.api.controller;

import com.wooseung.hancook.api.service.CartImageUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CartImageUploadController {
    private final CartImageUploadService cartImageUploadService;

    @PostMapping("/upload-image")
    public ResponseEntity<String> uploadImage(@RequestBody String base64Image) {
        String imageUrl = cartImageUploadService.uploadImageToImgbb(base64Image);
        return new ResponseEntity<>(imageUrl, HttpStatus.OK);
    }
}
