package com.wooseung.hancook.api.controller;


import com.wooseung.hancook.api.service.MartService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mart")
@RequiredArgsConstructor
public class MartController {

    private final MartService martService;
    private static final Logger logger = LoggerFactory.getLogger(MartController.class);

    @GetMapping
    public ResponseEntity<?> test(@RequestParam String name){
        martService.craw(name);
        return null;
    }
}
