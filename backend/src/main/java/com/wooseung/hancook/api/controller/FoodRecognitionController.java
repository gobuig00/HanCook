package com.wooseung.hancook.api.controller;

import com.wooseung.hancook.api.service.FoodRecognitionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/food")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class FoodRecognitionController {

    private final FoodRecognitionService foodRecognitionService;

    @PostMapping("/recognize")
    public ResponseEntity<String> recognizeFood(@RequestParam("image") MultipartFile image) {
        try {
            String result = foodRecognitionService.recognizeFood(image);
            return ResponseEntity.ok(result);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}