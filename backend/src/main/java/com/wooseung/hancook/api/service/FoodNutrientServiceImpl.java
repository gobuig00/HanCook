package com.wooseung.hancook.api.service;

import com.wooseung.hancook.api.response.FoodNutrientResponseDto;
import com.wooseung.hancook.api.response.IngredientResponseDto;
import com.wooseung.hancook.db.entity.FoodNutrient;
import com.wooseung.hancook.db.entity.Ingredient;
import com.wooseung.hancook.db.repository.FoodNutrientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service("foodNutrientServiceService")
@RequiredArgsConstructor
@Slf4j
public class FoodNutrientServiceImpl implements FoodNutrientService {

    private final FoodNutrientRepository foodNutrientRepository;

    private final PapagoTranslationService papagoTranslationService;
    private final DetectLanguageService detectLanguageService;
    private final IngredientService ingredientService;

    @Override
    public FoodNutrientResponseDto getNutrientById(Long id) {
        // 이름으로 찾은 레시피 Entity List
        Optional<FoodNutrient> foodNutrientEntity = foodNutrientRepository.findById(id);
        FoodNutrientResponseDto foodNutrientResponseDto = FoodNutrientResponseDto.of(foodNutrientEntity.get());

        return foodNutrientResponseDto;
    }

    @Override
    public FoodNutrientResponseDto getNutrientByName(String name) {
        int flag = detectLanguageService.detectLanguage(name);

        // 입력받은 이름이 영어라면 한글로 변환
        if (flag == 1) name = papagoTranslationService.translateKoreanIntoEnglish(name);

        // 이름으로 찾은 레시피 Entity List
        FoodNutrient foodNutrientEntity = foodNutrientRepository.findByName(name);
        FoodNutrientResponseDto foodNutrientResponseDto = FoodNutrientResponseDto.of(foodNutrientEntity);

        return foodNutrientResponseDto;
    }

    @Override
    public FoodNutrientResponseDto getNutrientByIngredientId(Long ingredientId) {
        IngredientResponseDto ingredientResponseDto = ingredientService.getIngredientByIngredientId(ingredientId, 0);
        return getNutrientByName(ingredientResponseDto.getName());
    }

}
