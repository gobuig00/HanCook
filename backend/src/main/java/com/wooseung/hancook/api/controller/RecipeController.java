package com.wooseung.hancook.api.controller;

import com.wooseung.hancook.api.response.ProcessResponseDto;
import com.wooseung.hancook.api.response.RecipeResponseDto;
import com.wooseung.hancook.api.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * 레시피 API
 */
@RestController
@RequestMapping("/recipe")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class RecipeController {

    private final RecipeService recipeService;

//    @ApiOperation(value = "채식 카테고리를 입력받아 일치하는 레시피 데이터를 반환", response = List.class)
//    @GetMapping("/category")
//    public ResponseEntity<List<RecipeDto>> getRecipeByCategory(@RequestParam("category") String category){
//        List<RecipeDto> recipeDtoList = recipeService.getRecipeByVeg(category);
//        return ResponseEntity.status(HttpStatus.OK).body(recipeDtoList);
//    }

    // 랜덤으로 레시피 3개 받아오기
    @GetMapping("/random")
    public ResponseEntity<List<RecipeResponseDto>> getRandomRecipe(){
        List<RecipeResponseDto> recipeResponseDtoList = recipeService.getRandomRecipe();
        return ResponseEntity.status(HttpStatus.OK).body(recipeResponseDtoList);
    }
    
    // 이름을 입력받아 일치하는 레시피 검색
    @GetMapping("/name")
    public ResponseEntity<List<RecipeResponseDto>> getRecipeByName(@RequestParam("name") String name){
        List<RecipeResponseDto> recipeResponseDtoList = recipeService.getRecipeByName(name);
        return ResponseEntity.status(HttpStatus.OK).body(recipeResponseDtoList);
    }

    // 레시피 ID를 입력받아 일치하는 레시피 데이터 및 재료, 과정 데이터 반환
    @GetMapping("/id")
    public ResponseEntity<Map<String, Object>> getRecipeById(@RequestParam("recipeId") Long recipeId){
        Map<String, Object> result = new HashMap<>();
        result.put("recipe", recipeService.getRecipeById(recipeId));
        result.put("ingredient", recipeService.getIngredientByRecipeId(recipeId));
        result.put("process", recipeService.getProcessByRecipeId(recipeId));
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // 재료 배열을 입력받아 포함되어 있는 레시피 데이터 반환
    @GetMapping("/ingredient")
    public ResponseEntity<List<RecipeResponseDto>> getRecipeByIngredient(@RequestParam("ingredient") List<String> ingredient){
        List<RecipeResponseDto> recipeResponseDtoList = recipeService.getRecipeByIngredient(ingredient);
        return ResponseEntity.status(HttpStatus.OK).body(recipeResponseDtoList);
    }

    // 이름이나 재료를 입력받아 포함되어 있는 레시피 데이터 반환
    @GetMapping("/search")
    public ResponseEntity<List<RecipeResponseDto>> searchRecipe(@RequestParam("name") String name){
        List<RecipeResponseDto> recipeResponseDtoListByName = recipeService.getRecipeByName(name);
        List<String> strList = new ArrayList<>();
        strList.add(name);
        List<RecipeResponseDto> recipeResponseDtoListByIngredient = recipeService.getRecipeByIngredient(strList);

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
    public ResponseEntity<List<ProcessResponseDto>> getProcessByRecipe(@RequestParam("recipeId") Long recipeId){
        List<ProcessResponseDto> processResponseDtoList = recipeService.getProcessByRecipeId(recipeId);
        return ResponseEntity.status(HttpStatus.OK).body(processResponseDtoList);
    }

}
