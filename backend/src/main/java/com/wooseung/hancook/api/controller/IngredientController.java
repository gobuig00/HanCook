package com.wooseung.hancook.api.controller;

import com.wooseung.hancook.api.response.IngredientCardResponseDto;
import com.wooseung.hancook.api.response.IngredientResponseDto;
import com.wooseung.hancook.api.service.IngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/ingredient")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class IngredientController {

    private final IngredientService ingredientService;

    @GetMapping("/Popular")
    public ResponseEntity<List<IngredientResponseDto>> getRandomIngredient(@RequestParam("lan") int lan) {
        List<IngredientResponseDto> ingredientResponseDtoList = ingredientService.getRandomIngredient(lan);
        return ResponseEntity.status(HttpStatus.OK).body(ingredientResponseDtoList);
    }

    @GetMapping("/Meat")
    public ResponseEntity<List<IngredientResponseDto>> getRandomMeatIngredient(@RequestParam("lan") int lan) {
        List<IngredientResponseDto> ingredientResponseDtoList = ingredientService.getRandomMeatIngredient(lan);
        return ResponseEntity.status(HttpStatus.OK).body(ingredientResponseDtoList);
    }

    @GetMapping("/Vegetable")
    public ResponseEntity<List<IngredientResponseDto>> getRandomVegetableIngredient(@RequestParam("lan") int lan) {
        List<IngredientResponseDto> ingredientResponseDtoList = ingredientService.getRandomVegetableIngredient(lan);
        return ResponseEntity.status(HttpStatus.OK).body(ingredientResponseDtoList);
    }

    //    // 이름 입력받아 재료 정보 반환
//    @GetMapping("/getbyname")
//    public ResponseEntity<IngredientResponseDto> getIngredientByName(@RequestParam("name") String name, @RequestParam("lan") int lan) {
//        IngredientResponseDto ingredientResponseDto = ingredientService.getIngredientByName(name, lan);
//        return ResponseEntity.status(HttpStatus.OK).body(ingredientResponseDto);
//    }

    // 이름 입력받아 재료 정보 반환
    @GetMapping("/getbyname")
    public ResponseEntity<List<IngredientResponseDto>> getIngredientByName(@RequestParam("name") String[] names, @RequestParam("lan") int lan) {
        List<IngredientResponseDto> ingredientResponseDtoList = new ArrayList<>();

        for (String name : names) {
            if (ingredientService.searchName(name) == 1) {
                IngredientResponseDto ingredientResponseDto = ingredientService.getIngredientByName(name, lan);
                ingredientResponseDtoList.add(ingredientResponseDto);
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body(ingredientResponseDtoList);
    }

    // 대분류 반환
    @GetMapping("/large")
    public ResponseEntity<List<String>> getLarge(@RequestParam("lan") int lan) {
        List<String> largeList = ingredientService.getLargeList(lan);
        return ResponseEntity.status(HttpStatus.OK).body(largeList);
    }

    // 대분류 입력받아 중분류 반환
    @GetMapping("/medium")
    public ResponseEntity<List<String>> getMedium(@RequestParam("large") String large, @RequestParam("lan") int lan) {
        List<String> mediumList = ingredientService.getMediumList(large, lan);
        return ResponseEntity.status(HttpStatus.OK).body(mediumList);
    }

    // 중분류 입력받아 재료명 반환
    @GetMapping("/name")
    public ResponseEntity<List<String>> getName(@RequestParam("medium") String medium, @RequestParam("lan") int lan) {
        List<String> nameList = ingredientService.getNameList(medium, lan);
        return ResponseEntity.status(HttpStatus.OK).body(nameList);
    }

    // id 입력받아 재료 정보 반환
    @GetMapping("/id")
    public ResponseEntity<IngredientResponseDto> getIngredientById(@RequestParam("ingredientId") Long ingredientId, @RequestParam("lan") int lan) {
        IngredientResponseDto ingredientResponseDto = ingredientService.getIngredientByIngredientId(ingredientId, lan);
        return ResponseEntity.status(HttpStatus.OK).body(ingredientResponseDto);
    }

    // ingredient card에 들어갈 정보들 반환
    @GetMapping("/card")
    public ResponseEntity<IngredientCardResponseDto> getIngredientCardById(@RequestParam("ingredientId") Long ingredientId, @RequestParam("lan") int lan) {
        IngredientCardResponseDto ingredientCardResponseDto = ingredientService.getIngredientCardByIngredientId(ingredientId, lan);
        return ResponseEntity.status(HttpStatus.OK).body(ingredientCardResponseDto);
    }

    // 재료명 입력받아 재료가 재료 목록에 있으면 1, 없으면 0 반환
    @GetMapping
    public ResponseEntity<Integer> searchName(@RequestParam("keyword") String keyword) {
        int result = ingredientService.searchName(keyword);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

}
