package com.wooseung.hancook.api.service;

import com.wooseung.hancook.api.response.IngredientCardResponseDto;
import com.wooseung.hancook.api.response.IngredientResponseDto;
import com.wooseung.hancook.api.response.RecipeResponseDto;
import com.wooseung.hancook.db.entity.Ingredient;
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

    private final PapagoTranslationService papagoTranslationService;
    private final PronunciationService pronunciationService;

    @Override
    public List<IngredientResponseDto> getRandomIngredient(int lan) {
        // 랜덤 재료 정보 Entity List
        List<Ingredient> ingredientEntityList = ingredientRepository.findRandomIngredient();

        // 반환할 레시피 Dto List
        List<IngredientResponseDto> ingredientResponseDtoList = new ArrayList<>();

        for (Ingredient ingredientEntity : ingredientEntityList) {
            IngredientResponseDto ingredientResponseDto = IngredientResponseDto.of(ingredientEntity);

            // 영문일때
            if (lan == 1) {
                // DB에 한글로 저장되어 있어 영어로 번역해서 response
                ingredientResponseDto.setName(papagoTranslationService.translateKoreanIntoEnglish(ingredientEntity.getName()));
            }

            // 반환할 DtoList에 현재 RecipeEntity를 Dto로 변환하여 추가
            ingredientResponseDtoList.add(ingredientResponseDto);
        }

        return ingredientResponseDtoList;
    }

    @Override
    public List<IngredientResponseDto> getRandomMeatIngredient(int lan) {
        // 랜덤 재료 정보 Entity List
        List<Ingredient> ingredientEntityList = ingredientRepository.findRandomMeatIngredient();

        // 반환할 레시피 Dto List
        List<IngredientResponseDto> ingredientResponseDtoList = new ArrayList<>();

        for (Ingredient ingredientEntity : ingredientEntityList) {
            IngredientResponseDto ingredientResponseDto = IngredientResponseDto.of(ingredientEntity);

            // 영문일때
            if (lan == 1) {
                // DB에 한글로 저장되어 있어 영어로 번역해서 response
                ingredientResponseDto.setName(papagoTranslationService.translateKoreanIntoEnglish(ingredientEntity.getName()));
            }

            // 반환할 DtoList에 현재 RecipeEntity를 Dto로 변환하여 추가
            ingredientResponseDtoList.add(ingredientResponseDto);
        }

        return ingredientResponseDtoList;
    }

    @Override
    public List<IngredientResponseDto> getRandomVegetableIngredient(int lan) {
        // 랜덤 재료 정보 Entity List
        List<Ingredient> ingredientEntityList = ingredientRepository.findRandomVegetableIngredient();

        // 반환할 레시피 Dto List
        List<IngredientResponseDto> ingredientResponseDtoList = new ArrayList<>();

        for (Ingredient ingredientEntity : ingredientEntityList) {
            IngredientResponseDto ingredientResponseDto = IngredientResponseDto.of(ingredientEntity);

            // 영문일때
            if (lan == 1) {
                // DB에 한글로 저장되어 있어 영어로 번역해서 response
                ingredientResponseDto.setName(papagoTranslationService.translateKoreanIntoEnglish(ingredientEntity.getName()));
            }

            // 반환할 DtoList에 현재 RecipeEntity를 Dto로 변환하여 추가
            ingredientResponseDtoList.add(ingredientResponseDto);
        }

        return ingredientResponseDtoList;
    }

    @Override
    public List<String> getLargeList(int lan) {
        List<String> largeList = ingredientRepository.findLarge();

        if (lan == 1) {
            for (String large : largeList) {
                large = papagoTranslationService.translateKoreanIntoEnglish(large);
            }
        }

        return largeList;
    }

    @Override
    public List<String> getMediumList(String large, int lan) {
        List<String> mediumList = ingredientRepository.findMedium(large);

        if (lan == 1) {
            for (String medium : mediumList) {
                medium = papagoTranslationService.translateKoreanIntoEnglish(medium);
            }
        }

        return mediumList;
    }

    @Override
    public List<String> getNameList(String medium, int lan) {
        List<String> nameList = new ArrayList<>();
        List<Ingredient> ingredientList = ingredientRepository.findIngredientByMedium(medium);

        // ref 값이 존재하는 ingredient는 제외하고 반환(이름이 애매한 것들 제외)
        for (Ingredient ingredient : ingredientList) {
            if (ingredient.getRef() == null) {
                nameList.add(ingredient.getName());
            }
        }

        if (lan == 1) {
            for (String name : nameList) {
                name = papagoTranslationService.translateKoreanIntoEnglish(name);
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

    @Override
    public String searchById(Long id){
        Optional<Ingredient> ingredientName = ingredientRepository.findByIngredientId(id);
        if(ingredientName.isEmpty())   return "Empty";
        else return ingredientName.get().getName();
    }

    @Override
    public Long getIngredientIdByName(String name) {
        Optional<Ingredient> ingredient = ingredientRepository.findIngredientByName(name);
        return ingredient.get().getIngredientId();
    }

    @Override
    public IngredientResponseDto getIngredientByIngredientId(Long ingredientId, int lan) {
        Optional<Ingredient> ingredient = ingredientRepository.findByIngredientId(ingredientId);

        IngredientResponseDto ingredientResponseDto = IngredientResponseDto.of(ingredient.get());

        // 영문일때
        if (lan == 1) {
            ingredientResponseDto.setLarge(papagoTranslationService.translateKoreanIntoEnglish(ingredientResponseDto.getLarge()));
            ingredientResponseDto.setMedium(papagoTranslationService.translateKoreanIntoEnglish(ingredientResponseDto.getMedium()));
            ingredientResponseDto.setName(papagoTranslationService.translateKoreanIntoEnglish(ingredientResponseDto.getName()));
        }

        return ingredientResponseDto;
    }

    @Override
    public IngredientResponseDto getIngredientByName(String name, int lan) {
        Optional<Ingredient> ingredient = ingredientRepository.findIngredientByName(name);

        IngredientResponseDto ingredientResponseDto = IngredientResponseDto.of(ingredient.get());

        // 영문일때
        if (lan == 1) {
            ingredientResponseDto.setLarge(papagoTranslationService.translateKoreanIntoEnglish(ingredientResponseDto.getLarge()));
            ingredientResponseDto.setMedium(papagoTranslationService.translateKoreanIntoEnglish(ingredientResponseDto.getMedium()));
            ingredientResponseDto.setName(papagoTranslationService.translateKoreanIntoEnglish(ingredientResponseDto.getName()));
        }

        return ingredientResponseDto;
    }

    @Override
    public IngredientCardResponseDto getIngredientCardByIngredientId(Long ingredientId, int lan) {
        Optional<Ingredient> ingredient = ingredientRepository.findByIngredientId(ingredientId);

        IngredientCardResponseDto ingredientCardResponseDto = IngredientCardResponseDto.of(ingredient.get());

        // 영문일때
        if (lan == 1) {
            ingredientCardResponseDto.setLarge(papagoTranslationService.translateKoreanIntoEnglish(ingredientCardResponseDto.getLarge()));
            ingredientCardResponseDto.setMedium(papagoTranslationService.translateKoreanIntoEnglish(ingredientCardResponseDto.getMedium()));
        }

        ingredientCardResponseDto.setEngName(papagoTranslationService.translateKoreanIntoEnglish(ingredientCardResponseDto.getName()));
        ingredientCardResponseDto.setPronunciation(pronunciationService.conversionPronunciation(ingredientCardResponseDto.getName()));

        return ingredientCardResponseDto;
    }
}
