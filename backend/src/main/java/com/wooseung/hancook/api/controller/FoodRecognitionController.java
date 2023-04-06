package com.wooseung.hancook.api.controller;

import com.wooseung.hancook.api.response.CheckFoodResponseDto;
import com.wooseung.hancook.api.response.RecipeResponseDto;
import com.wooseung.hancook.api.service.FoodRecognitionService;
import com.wooseung.hancook.api.service.IngredientService;
import com.wooseung.hancook.api.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;

@RestController
@RequestMapping("/food")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class FoodRecognitionController {

    private static final Logger logger = LogManager.getLogger(FoodRecognitionController.class);
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

    // -1 : 해당 없음 / 1 : 음식 / 2 : 재료
    @GetMapping("/check")
    public ResponseEntity<CheckFoodResponseDto> checkFood(@RequestParam("name") String name) {
        logger.info("name" + name);
        StringTokenizer st = new StringTokenizer(name, ",");
        name = st.nextToken();
        logger.info("realName" + name);

        int checkFlag = -1;
        Long id = 1L;

        // 레시피 테이블에 이름이 있는지 체크
        int recipeResult = recipeService.searchName(name);
        // 재료 테이블에 이름이 있는지 체크
        int ingredientResult = ingredientService.searchName(name);

        // 레시피 테이블에만 이름이 있다면 재료로 취급 result = 1
        if (recipeResult == 1 && ingredientResult == 0) {
            checkFlag = 1;
            id = recipeService.getRecipeIdByName(name);
        }
        // 재료 테이블에만 이름이 있다면 result = 2
        else if (recipeResult == 0 && ingredientResult == 1) {
            checkFlag = 2;
            id = ingredientService.getIngredientIdByName(name);
        }
        // 둘 다 있다면 재료로 리턴
        else if (recipeResult == 1 && ingredientResult == 1) {
            checkFlag = 2;
            id = ingredientService.getIngredientIdByName(name);
        }

        return ResponseEntity.status(HttpStatus.OK).body(new CheckFoodResponseDto(checkFlag, id));
    }
}