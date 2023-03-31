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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CartController {

    private final CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<BaseResponseBody> addToCartByRecipeId(@RequestParam("recipeId") Long recipeId, @AuthenticationPrincipal UserDetails userDetails) {
        cartService.addIngredientToCartByRecipeId(recipeId, userDetails.getEmail());
        return new ResponseEntity<>(new BaseResponseBody("SUCCESS", 201), HttpStatus.CREATED);
    }

    @PostMapping("/addComponent")
    public ResponseEntity<BaseResponseBody> addToCartByComponentId(@RequestParam("componentId") Long componentId, @AuthenticationPrincipal UserDetails userDetails) {
        cartService.addIngredientToCartByComponentId(componentId, userDetails.getEmail());
        return new ResponseEntity<>(new BaseResponseBody("SUCCESS", 201), HttpStatus.CREATED);
    }

//    @GetMapping("/get")
//    public ResponseEntity<List<FoodRecordResponseDto>> getFoodRecordByUser(@AuthenticationPrincipal UserDetails userDetails) {
//        Optional<User> user = userRepository.findByEmail(userDetails.getEmail());
//        return ResponseEntity.status(HttpStatus.OK).body(foodRecordService.getFoodRecordById(user.get().getId()));
//    }
}
