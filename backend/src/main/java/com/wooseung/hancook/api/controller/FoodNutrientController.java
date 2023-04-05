package com.wooseung.hancook.api.controller;

import com.wooseung.hancook.api.response.FoodNutrientResponseDto;
import com.wooseung.hancook.api.response.FoodResponseDto;
import com.wooseung.hancook.api.service.FoodNutrientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/nutrient")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class FoodNutrientController {
    private final FoodNutrientService foodNutrientService;

    @GetMapping("/food/{name}")
    public ResponseEntity<FoodNutrientResponseDto> getNutrientByName(@PathVariable String name) {
        return ResponseEntity.status(HttpStatus.OK).body(foodNutrientService.getNutrientByName(name));
    }

    @GetMapping("/ingredient")
    public ResponseEntity<FoodNutrientResponseDto> getNutrientByIngredientId(@RequestParam("ingredientId") Long ingredientId) {
        return ResponseEntity.status(HttpStatus.OK).body(foodNutrientService.getNutrientByIngredientId(ingredientId));
    }

}
