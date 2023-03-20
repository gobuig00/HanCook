package com.wooseung.hancook.api.controller;

import com.wooseung.hancook.api.response.CrawlingResponseDto;
import com.wooseung.hancook.api.service.CrawlingService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/crawling")
@RequiredArgsConstructor
public class CrawlingController {
    private final CrawlingService martservice;
    private static final Logger logger = LoggerFactory.getLogger(CrawlingController.class);

    @GetMapping
    public ResponseEntity<?> craw(@RequestParam String keyword){
        logger.info(keyword);
        CrawlingResponseDto mart = martservice.craw(keyword);
        return null;
    }

}