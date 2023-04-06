package com.wooseung.hancook.api.service;

import com.wooseung.hancook.api.response.CartResponseDto;

import java.util.List;

public interface CartService {
    void addIngredientToCartByRecipeId(Long recipeId, String email);
    void addIngredientToCartByIngredientId(Long componentId, String email);

    List<CartResponseDto> getCartByEmail(String email);

}
