package com.wooseung.hancook.api.controller;

import com.wooseung.hancook.api.response.ProcessResponseDto;
import com.wooseung.hancook.api.response.RecipeResponseDto;
import com.wooseung.hancook.api.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/*
 * 레시피 API
 */
@RestController
@RequestMapping("/recipe")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class RecipeController {

    private final RecipeService recipeService;
    private static final Logger logger = LogManager.getLogger(RecipeController.class);

    // 랜덤으로 레시피 3개 받아오기
    @GetMapping("/Popular")
    public ResponseEntity<List<RecipeResponseDto>> getRandomRecipe(@RequestParam("lan") int lan) {
        List<RecipeResponseDto> recipeResponseDtoList = recipeService.getRandomRecipe(lan);
        return ResponseEntity.status(HttpStatus.OK).body(recipeResponseDtoList);
    }

    // 이름을 입력받아 일치하는 레시피 검색
    @GetMapping("/name")
    public ResponseEntity<List<RecipeResponseDto>> getRecipeByName(@RequestParam("name") String name, @RequestParam("lan") int lan) {
        List<RecipeResponseDto> recipeResponseDtoList = recipeService.getRecipeByName(name, lan);
        return ResponseEntity.status(HttpStatus.OK).body(recipeResponseDtoList);
    }

    // 레시피 ID를 입력받아 일치하는 레시피 데이터 및 재료, 과정 데이터 반환
    @GetMapping("/id")
    public ResponseEntity<Map<String, Object>> getRecipeById(@RequestParam("recipeId") Long recipeId, @RequestParam("lan") int lan) {
        Map<String, Object> result = new HashMap<>();
        result.put("recipe", recipeService.getRecipeById(recipeId, lan));
        result.put("ingredient", recipeService.getIngredientByRecipeId(recipeId, lan));
        result.put("process", recipeService.getProcessByRecipeId(recipeId, lan));
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // 레시피 ID를 입력받아 일치하는 레시피 데이터 및 재료, 과정 데이터 반환
    @GetMapping("/card")
    public ResponseEntity<Map<String, Object>> getRecipeCardById(@RequestParam("recipeId") Long recipeId, @RequestParam("lan") int lan) {
        Map<String, Object> result = new HashMap<>();

        result.put("recipe", recipeService.getRecipeCardById(recipeId, lan));
        result.put("ingredient", recipeService.getIngredientByRecipeId(recipeId, lan));
        result.put("process", recipeService.getProcessByRecipeId(recipeId, lan));
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // 재료 배열을 입력받아 포함되어 있는 레시피 데이터 반환
    @GetMapping("/ingredient")
    public ResponseEntity<List<RecipeResponseDto>> getRecipeByIngredient(@RequestParam("ingredient") List<String> ingredient, @RequestParam("lan") int lan) {
        List<RecipeResponseDto> recipeResponseDtoList = recipeService.getRecipeByIngredient(ingredient, lan);
        return ResponseEntity.status(HttpStatus.OK).body(recipeResponseDtoList);
    }

    // 이름이나 재료를 입력받아 포함되어 있는 레시피 데이터 반환
    @GetMapping("/search")
    public ResponseEntity<List<RecipeResponseDto>> searchRecipe(@RequestParam("name") String name, @RequestParam("lan") int lan) {
        logger.info("name" + name);

        StringTokenizer st = new StringTokenizer(name, ",");
        name = st.nextToken();

        List<RecipeResponseDto> recipeResponseDtoListByName = recipeService.getRecipeByName(name, lan);
        logger.info("recipeResponseDtoListByName" + recipeResponseDtoListByName.get(0));
        List<String> strList = new ArrayList<>();
        strList.add(name);
        List<RecipeResponseDto> recipeResponseDtoListByIngredient = recipeService.getRecipeByIngredient(strList, lan);

        List<RecipeResponseDto> answerList = new ArrayList<>();
        // 이름을 입력받아 일치한 레시피 데이터 리스트
        for (RecipeResponseDto recipeResponseDto : recipeResponseDtoListByName) {
            // 중복 제거
            if (!answerList.contains(recipeResponseDto)) answerList.add(recipeResponseDto);
        }
        // 재료명을 입력받아 일치한 레시피 데이터 리스트
        for (RecipeResponseDto recipeResponseDto : recipeResponseDtoListByIngredient) {
            // 중복 제거
            if (!answerList.contains(recipeResponseDto)) answerList.add(recipeResponseDto);
        }

        return ResponseEntity.status(HttpStatus.OK).body(answerList);
    }

    // 레시피 아이디를 입력받아 레시피 요리 순서, 설명 반환
    @GetMapping("/process")
    public ResponseEntity<List<ProcessResponseDto>> getProcessByRecipe(@RequestParam("recipeId") Long recipeId, @RequestParam("lan") int lan) {
        List<ProcessResponseDto> processResponseDtoList = recipeService.getProcessByRecipeId(recipeId, lan);
        return ResponseEntity.status(HttpStatus.OK).body(processResponseDtoList);
    }

}
