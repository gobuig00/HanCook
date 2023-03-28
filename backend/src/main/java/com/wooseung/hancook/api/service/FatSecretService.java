package com.wooseung.hancook.api.service;

import com.wooseung.hancook.api.response.FoodResponseDto;
import com.wooseung.hancook.api.response.FoodSearchResponseDto;

import java.io.IOException;

public interface FatSecretService {
    FoodResponseDto getFood(String name) throws Exception;
    FoodSearchResponseDto searchFood(String query) throws Exception;
}
