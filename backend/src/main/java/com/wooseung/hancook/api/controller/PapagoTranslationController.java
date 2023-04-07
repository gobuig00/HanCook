package com.wooseung.hancook.api.controller;

import com.wooseung.hancook.api.service.PapagoTranslationService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.StringTokenizer;

@RestController
@RequestMapping("/translate")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PapagoTranslationController {

    private static final Logger logger = LogManager.getLogger(PapagoTranslationController.class);
    private final PapagoTranslationService papagoTranslationService;

    // 한국어 텍스트를 영어로 번역
    @GetMapping("/koreantoenglish")
    public ResponseEntity<String> translateKoreanToEnglish(@RequestParam("text") String text) {
        logger.info("text" + text);
        StringTokenizer st = new StringTokenizer(text, ",");
        text = st.nextToken();
        logger.info("realName" + text);

        String result = papagoTranslationService.translateKoreanIntoEnglish(text);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // 영어 텍스트를 한국어로 번역
    @GetMapping("/englishtokorean")
    public ResponseEntity<String> translateEnglishToKorean(@RequestParam("text") String text) {
        logger.info("text" + text);
        StringTokenizer st = new StringTokenizer(text, ",");
        text = st.nextToken();
        logger.info("realName" + text);
        
        String result = papagoTranslationService.translateEnglishIntoKorean(text);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

}
