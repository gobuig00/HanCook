package com.wooseung.hancook.api.service;

import com.wooseung.hancook.api.response.MartResponseDto;
import com.wooseung.hancook.common.exception.ApiException;
import com.wooseung.hancook.common.exception.ExceptionEnum;
import com.wooseung.hancook.db.entity.Ingredient;
import com.wooseung.hancook.db.entity.Mart;
import com.wooseung.hancook.db.repository.IngredientRepository;
import com.wooseung.hancook.db.repository.MartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("martService")
@RequiredArgsConstructor
@Slf4j
public class MartServiceImpl implements MartService {


    private final MartRepository martRepository;
    private final IngredientRepository ingredientRepository;

    @Override
    public List<MartResponseDto> getMartList(String ingreName) {
        Optional<Ingredient> ingredient = ingredientRepository.findIngredientByName(ingreName);
        if (ingredient.isEmpty()) throw new ApiException(ExceptionEnum.INGREDIENT_NOT_EXIST_EXCEPTION);

        List<Mart> martEntityList = martRepository.findNineByIngredientId(ingredient.get().getIngredientId());

        return martEntityList.stream()
                .map(entity -> MartResponseDto.of(entity))
                .collect(Collectors.toList());
    }
}
