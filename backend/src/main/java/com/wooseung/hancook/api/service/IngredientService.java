package com.wooseung.hancook.api.service;

import com.wooseung.hancook.api.response.IngredientResponseDto;

import java.util.List;

public interface IngredientService {

    List<IngredientResponseDto> getRandomIngredient();

    List<String> getLargeList();

    List<String> getMediumList(String large);

    List<String> getNameList(String medium);

    int searchName(String name);

}
