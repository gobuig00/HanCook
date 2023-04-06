package com.wooseung.hancook.api.controller;

import com.wooseung.hancook.api.response.BaseResponseBody;
import com.wooseung.hancook.api.response.ComponentResponseDto;
import com.wooseung.hancook.api.service.CartService;
import com.wooseung.hancook.api.service.RecipeService;
import com.wooseung.hancook.common.auth.UserDetails;
import com.wooseung.hancook.db.entity.Component;
import com.wooseung.hancook.db.entity.Ingredient;
import com.wooseung.hancook.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.StringTokenizer;


@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CartController {

    private static final Logger logger = LogManager.getLogger(CartController.class);
    private final CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<BaseResponseBody> addToCartByRecipeId(@RequestParam("recipeId") String recipeId, @AuthenticationPrincipal UserDetails userDetails) {
        logger.info("recipeId" + recipeId);
        StringTokenizer st = new StringTokenizer(recipeId, ",");
        recipeId = st.nextToken();
        logger.info("realName" + recipeId);

        cartService.addIngredientToCartByRecipeId(Long.parseLong(recipeId), userDetails.getEmail());
        return new ResponseEntity<>(new BaseResponseBody("SUCCESS", 201), HttpStatus.CREATED);
    }

    @PostMapping("/addComponent")
    public ResponseEntity<BaseResponseBody> addToCartByComponentId(@RequestParam("componentId") Long componentId, @AuthenticationPrincipal UserDetails userDetails) {
        cartService.addIngredientToCartByComponentId(componentId, userDetails.getEmail());
        return new ResponseEntity<>(new BaseResponseBody("SUCCESS", 201), HttpStatus.CREATED);
    }

}
