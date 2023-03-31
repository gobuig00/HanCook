package com.wooseung.hancook.api.service;

import com.wooseung.hancook.api.response.IngredientResponseDto;

import java.util.List;

public interface IngredientService {

    List<IngredientResponseDto> getRandomIngredient(int lan);
    List<IngredientResponseDto> getRandomMeatIngredient(int lan);
    List<IngredientResponseDto> getRandomVegetableIngredient(int lan);

    List<String> getLargeList(int lan);

    List<String> getMediumList(String large, int lan);

    List<String> getNameList(String medium, int lan);

    int searchName(String name);

}
