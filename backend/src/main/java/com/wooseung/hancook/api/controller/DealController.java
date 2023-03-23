package com.wooseung.hancook.api.controller;

import com.wooseung.hancook.api.response.DealResponseDto;
import com.wooseung.hancook.api.service.DealService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/deal")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class DealController {
    private final DealService dealService;

    @GetMapping("/large")
    public ResponseEntity<List<String>> getLarge() {
        List<String> largeList = dealService.getLarge();
        return ResponseEntity.status(HttpStatus.OK).body(largeList);
    }

    @GetMapping("/medium")
    public ResponseEntity<List<String>> getMedium(@RequestParam("large") String large) {
        List<String> mediumList = dealService.getMedium(large);
        return ResponseEntity.status(HttpStatus.OK).body(mediumList);
    }

    @GetMapping("/small")
    public ResponseEntity<List<String>> getSmall(@RequestParam("large") String large, @RequestParam("medium") String medium) {
        List<String> smallList = dealService.getSmall(large, medium);
        return ResponseEntity.status(HttpStatus.OK).body(smallList);
    }

    @GetMapping("/origin")
    public ResponseEntity<List<String>> getOrigin(@RequestParam("large") String large, @RequestParam("medium") String medium, @RequestParam("small") String small) {
        List<String> originList = dealService.getOrigin(large, medium, small);
        return ResponseEntity.status(HttpStatus.OK).body(originList);
    }

    @GetMapping("/")
    public ResponseEntity<List<DealResponseDto>> getDeal(@RequestParam("large") String large, @RequestParam("medium") String medium, @RequestParam("small") String small, @RequestParam("origin") String origin) {
        List<DealResponseDto> dealDtoList = dealService.getDeal(large, medium, small, origin);
        return ResponseEntity.status(HttpStatus.OK).body(dealDtoList);
    }

}