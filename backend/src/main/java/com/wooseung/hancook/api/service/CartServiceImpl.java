package com.wooseung.hancook.api.service;

import com.wooseung.hancook.api.response.*;
import com.wooseung.hancook.common.exception.ApiException;
import com.wooseung.hancook.common.exception.ExceptionEnum;
import com.wooseung.hancook.db.entity.Cart;
import com.wooseung.hancook.db.entity.Component;
import com.wooseung.hancook.db.entity.FoodRecord;
import com.wooseung.hancook.db.entity.User;
import com.wooseung.hancook.db.repository.CartRepository;
import com.wooseung.hancook.db.repository.ComponentRepository;
import com.wooseung.hancook.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service("cartService")
@RequiredArgsConstructor
@Slf4j
public class CartServiceImpl implements CartService {

    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final ComponentRepository componentRepository;

    private final RecipeService recipeService;
    private final PapagoTranslationService papagoTranslationService;

    @Override
    @Transactional
    public void addIngredientToCartByRecipeId(Long recipeId, String email) {
        // 레시피 아이디로 재료 리스트 받기
        List<ComponentResponseDto> componentResponseDtoList = recipeService.getIngredientByRecipeId(recipeId, 0);

        // 이메일에 맞는 유저 검색
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ApiException(ExceptionEnum.MEMBER_NOT_EXIST_EXCEPTION));

        for (ComponentResponseDto componentResponseDto : componentResponseDtoList) {
            String componentName = componentResponseDto.getName();
            cartRepository.save(new Cart(user, componentName));
        }
    }

    @Override
    @Transactional
    public void addIngredientToCartByComponentId(Long componentId, String email) {
        // 컴포넌트 아이디로 이름 찾기
        Component component = componentRepository.findById(componentId);

        // 이메일에 맞는 유저 검색
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ApiException(ExceptionEnum.MEMBER_NOT_EXIST_EXCEPTION));

        cartRepository.save(new Cart(user, component.getName()));
    }

    @Override
    public List<CartResponseDto> getCartByEmail(String email) {
        // 이메일에 맞는 유저 검색
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ApiException(ExceptionEnum.MEMBER_NOT_EXIST_EXCEPTION));

        List<Cart> cartEntityList = cartRepository.findAllByUserId(user.getId());

        return cartEntityList.stream()
                .map(entity -> CartResponseDto.of(entity))
                .collect(Collectors.toList());
    }
}
