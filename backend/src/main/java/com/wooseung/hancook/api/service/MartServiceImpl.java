package com.wooseung.hancook.api.service;

import com.wooseung.hancook.api.response.MartResponseDto;
import com.wooseung.hancook.db.entity.Ingredient;
import com.wooseung.hancook.db.entity.Mart;
import com.wooseung.hancook.db.repository.IngredientRepository;
import com.wooseung.hancook.db.repository.MartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

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
    public List<MartResponseDto> getMartList(Long ingredientId, int mart) {
        Optional<Ingredient> ingredient = ingredientRepository.findById(ingredientId);
        List<MartResponseDto> martDtoList = null;
        List<Mart> martEntityList = null;

        if (ingredient.isPresent()) {
            if (ingredient.get().getRef() == null) {
                martEntityList = martRepository.findAllByIngredientAndMartAndItemNameIsNotNull(ingredient.get(), mart);
            }
            else {
                ingredient = ingredientRepository.findById(ingredient.get().getRef());
                martEntityList = martRepository.findAllByIngredientAndMartAndItemNameIsNotNull(ingredient.get(), mart);
            }
            martDtoList = martEntityList.stream().map(entity -> MartResponseDto.of(entity)).collect(Collectors.toList());
        }

        return martDtoList;
    }
}
