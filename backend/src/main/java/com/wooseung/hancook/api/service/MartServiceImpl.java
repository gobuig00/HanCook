package com.wooseung.hancook.api.service;

import com.wooseung.hancook.api.response.CartResponseDto;
import com.wooseung.hancook.api.response.MartResponseDto;
import com.wooseung.hancook.db.entity.Ingredient;
import com.wooseung.hancook.db.entity.Mart;
import com.wooseung.hancook.db.repository.CartRepository;
import com.wooseung.hancook.db.repository.IngredientRepository;
import com.wooseung.hancook.db.repository.MartRepository;
import com.wooseung.hancook.db.repository.UserRepository;
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
        List<Mart> martEntityList = martRepository.findAllByIngredient(ingredient.get());

        return martEntityList.stream()
                .map(entity -> MartResponseDto.of(entity))
                .collect(Collectors.toList());
    }
}
