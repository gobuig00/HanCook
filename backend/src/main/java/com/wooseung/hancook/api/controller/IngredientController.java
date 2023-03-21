package com.wooseung.hancook.api.controller;

import com.wooseung.hancook.api.response.IngredientResponseDto;
import com.wooseung.hancook.api.service.IngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ingredient")
@RequiredArgsConstructor
public class IngredientController {

    private final IngredientService ingredientService;

    @GetMapping("/random")
    public ResponseEntity<List<IngredientResponseDto>> getRandomIngredient() {
        List<IngredientResponseDto> ingredientResponseDtoList = ingredientService.getRandomIngredient();
        return ResponseEntity.status(HttpStatus.OK).body(ingredientResponseDtoList);
    }

    // 대분류 반환
    @GetMapping("/large")
    public ResponseEntity<List<String>> getLarge() {
        List<String> largeList = ingredientService.getLargeList();
        return ResponseEntity.status(HttpStatus.OK).body(largeList);
    }

    // 대분류 입력받아 중분류 반환
    @GetMapping("/medium")
    public ResponseEntity<List<String>> getMedium(@RequestParam("large") String large) {
        List<String> mediumList = ingredientService.getMediumList(large);
        return ResponseEntity.status(HttpStatus.OK).body(mediumList);
    }

    // 중분류 입력받아 재료명 반환
    @GetMapping("/name")
    public ResponseEntity<List<String>> getName(@RequestParam("medium") String medium) {
        List<String> nameList = ingredientService.getNameList(medium);
        return ResponseEntity.status(HttpStatus.OK).body(nameList);
    }

    // 재료명 입력받아 재료가 재료 목록에 있으면 1, 없으면 0 반환
    @GetMapping
    public ResponseEntity<Integer> searchName(@RequestParam("keyword") String keyword) {
        int result = ingredientService.searchName(keyword);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

}
