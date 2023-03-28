package com.wooseung.hancook.api.controller;

import com.wooseung.hancook.api.service.PronunciationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pronunciation")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PronunciationController {

    private final PronunciationService pronunciationService;

    // 한글을 입력 받아 영어 발음 반환
    @GetMapping("/pronunciation")
    public ResponseEntity<String> getEnglishPronunciation(@RequestParam("korean") String korean) {
        String result = pronunciationService.conversionPronunciation(korean);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

}
