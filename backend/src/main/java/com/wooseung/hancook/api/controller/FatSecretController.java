package com.wooseung.hancook.api.controller;

import com.wooseung.hancook.api.response.FoodResponseDto;
import com.wooseung.hancook.api.service.FatSecretService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fatsecret")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class FatSecretController {
    private final FatSecretService fatSecretService;

    @GetMapping("/food/{name}")
    public ResponseEntity<FoodResponseDto> getFood(@PathVariable String name) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(fatSecretService.getFood(name));
    }

}
