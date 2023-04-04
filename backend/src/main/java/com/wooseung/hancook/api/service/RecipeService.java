package com.wooseung.hancook.api.service;


import com.wooseung.hancook.api.response.ComponentResponseDto;
import com.wooseung.hancook.api.response.ProcessResponseDto;
import com.wooseung.hancook.api.response.RecipeResponseDto;

import java.util.List;
import java.util.Optional;

public interface RecipeService {
    List<RecipeResponseDto> getRandomRecipe(int lan);

    RecipeResponseDto getRecipeById(Long recipeId, int lan);

    List<RecipeResponseDto> getRecipeByName(String name, int lan);

    List<RecipeResponseDto> getRecipeByIngredient(List<String> ingredient, int lan);

    List<ComponentResponseDto> getIngredientByRecipeId(Long recipeId, int lan);

    List<ProcessResponseDto> getProcessByRecipeId(Long recipeId, int lan);

    int searchName(String name);

    Long getRecipeIdByName(String name);
}
