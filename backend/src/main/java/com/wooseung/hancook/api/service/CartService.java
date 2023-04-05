package com.wooseung.hancook.api.service;

import com.wooseung.hancook.api.request.FoodRecordRequestDto;
import com.wooseung.hancook.api.response.CartResponseDto;
import com.wooseung.hancook.api.response.FoodRecordResponseDto;

import java.util.List;

public interface CartService {
    void addIngredientToCartByRecipeId(Long recipeId, String email);
    void addIngredientToCartByComponentId(Long componentId, String email);

    List<CartResponseDto> getCartByEmail(String email);

}
