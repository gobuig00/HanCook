package com.wooseung.hancook.api.controller;

import com.wooseung.hancook.api.response.MartResponseDto;
import com.wooseung.hancook.api.service.MartService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mart")
@RequiredArgsConstructor
public class MartController {
    private final MartService martservice;
    private static final Logger logger = LoggerFactory.getLogger(MartController.class);

    @GetMapping
    public ResponseEntity<?> craw(@RequestParam String keyword){
        logger.info(keyword);
        MartResponseDto mart = martservice.craw(keyword);
        return null;
    }

}
