package com.wooseung.hancook.api.controller;

import com.wooseung.hancook.api.response.RecipeResponseDto;
import com.wooseung.hancook.api.service.FoodRecognitionService;
import com.wooseung.hancook.api.service.IngredientService;
import com.wooseung.hancook.api.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/food")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class FoodRecognitionController {

    private final FoodRecognitionService foodRecognitionService;
    private final RecipeService recipeService;
    private final IngredientService ingredientService;

    @PostMapping("/recognize")
    public ResponseEntity<String> recognizeFood(@RequestParam("image") MultipartFile image) {
        try {
            String result = foodRecognitionService.recognizeFood(image);
            return ResponseEntity.ok(result);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 0 : 해당 없음 / 1 : 음식 / 2 : 재료
    @GetMapping("/check")
    public ResponseEntity<Integer> checkFood(@RequestParam("name") String name) {
        int result = 0;

        // 레시피 테이블에 이름이 있는지 체크
        int recipeResult = recipeService.searchName(name);
        // 재료 테이블에 이름이 있는지 체크
        int ingredientResult = ingredientService.searchName(name);
        
        // 레시피 테이블에만 이름이 있다면 result = 1
        if (recipeResult == 1 && ingredientResult == 0) result = 1;
        // 재료 테이블에만 이름이 있다면 result = 2
        else if (recipeResult == 0 && ingredientResult == 1) result = 2;
        // 둘 다 있다면 레시피로 리턴
        else if (recipeResult == 1 && ingredientResult == 1) result = 1;

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}