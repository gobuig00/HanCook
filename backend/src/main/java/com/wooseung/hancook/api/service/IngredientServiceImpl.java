package com.wooseung.hancook.api.service;

import com.wooseung.hancook.api.response.IngredientResponseDto;
import com.wooseung.hancook.api.response.RecipeResponseDto;
import com.wooseung.hancook.db.entity.Ingredient;
import com.wooseung.hancook.db.entity.Recipe;
import com.wooseung.hancook.db.repository.IngredientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository ingredientRepository;

    @Override
    public List<IngredientResponseDto> getRandomIngredient() {
        // 레시피에 포함된 전체 재료 정보 Entity List
        List<Ingredient> ingredientEntityList = ingredientRepository.findRandomIngredient();

        // 반환할 레시피 Dto List
        List<IngredientResponseDto> ingredientResponseDtoList = new ArrayList<>();

        for (Ingredient ingredientEntity : ingredientEntityList) {
            // 반환할 DtoList에 현재 RecipeEntity를 Dto로 변환하여 추가
            ingredientResponseDtoList.add(IngredientResponseDto.of(ingredientEntity));
        }

        return ingredientResponseDtoList;
    }

    @Override
    public List<String> getLargeList() {
        List<String> largeList = ingredientRepository.findLarge();
        return largeList;
    }

    @Override
    public List<String> getMediumList(String large) {
        List<String> mediumList = ingredientRepository.findMedium(large);
        return mediumList;
    }

    @Override
    public List<String> getNameList(String medium) {
        List<String> nameList = new ArrayList<>();
        List<Ingredient> ingredientList = ingredientRepository.findIngredientByMedium(medium);
        // ref 값이 존재하는 ingredient는 제외하고 반환(이름이 애매한 것들 제외)
        for(Ingredient ingredient: ingredientList){
            if(ingredient.getRef() == null){
                nameList.add(ingredient.getName());
            }
        }
        return nameList;
    }

    @Override
    public int searchName(String name) {
        Optional<Ingredient> ingredient = ingredientRepository.findIngredientByName(name);

        if (ingredient.isPresent()) return 1;
        else return 0;
    }
}
