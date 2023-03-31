package com.wooseung.hancook.api.service;

import com.wooseung.hancook.api.response.FoodNutrientResponseDto;

public interface FoodNutrientService {
    FoodNutrientResponseDto getNutrientById(Long id);
    FoodNutrientResponseDto getNutrientByName(String name);
}
