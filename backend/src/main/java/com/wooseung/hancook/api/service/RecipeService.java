package com.wooseung.hancook.api.service;


import com.wooseung.hancook.api.response.ComponentResponseDto;
import com.wooseung.hancook.api.response.ProcessResponseDto;
import com.wooseung.hancook.api.response.RecipeResponseDto;

import java.util.List;

public interface RecipeService {
//    List<RecipeResponseDto> getRecipeByVeg(String category);

    List<RecipeResponseDto> getRandomRecipe();
    List<RecipeResponseDto> getRandomEngRecipe();

    RecipeResponseDto getRecipeById(Long recipeId);
    RecipeResponseDto getEngRecipeById(Long recipeId);

    List<RecipeResponseDto> getRecipeByName(String name);

    List<RecipeResponseDto> getRecipeByIngredient(List<String> ingredient);

    List<ComponentResponseDto> getIngredientByRecipeId(Long recipeId);

    List<ProcessResponseDto> getProcessByRecipeId(Long recipeId);

}
