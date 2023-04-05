package com.wooseung.hancook.api.controller;

import com.wooseung.hancook.api.response.CartResponseDto;
import com.wooseung.hancook.api.response.MartResponseDto;
import com.wooseung.hancook.api.service.CartService;
import com.wooseung.hancook.api.service.MartService;
import com.wooseung.hancook.api.service.PapagoTranslationService;
import com.wooseung.hancook.common.auth.UserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mart")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MartController {

    private final CartService cartService;
    private final MartService martService;
    private final PapagoTranslationService papagoTranslationService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getMart(@AuthenticationPrincipal UserDetails userDetails, @RequestParam("lan") int lan) {
        Map<String, Object> result = new HashMap<>();

        String email = userDetails.getEmail();

        List<CartResponseDto> cartList = cartService.getCartByEmail(email);

        for (CartResponseDto cartResponseDto : cartList) {
            String ingredientName = cartResponseDto.getIngreName();
            List<MartResponseDto> martList = martService.getMartList(ingredientName);
            if (lan == 1) ingredientName = papagoTranslationService.translateKoreanIntoEnglish(ingredientName);
            result.put(ingredientName, martList);
        }

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
