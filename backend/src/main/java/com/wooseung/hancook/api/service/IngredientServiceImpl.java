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

    private final PapagoTranslationService papagoTranslationService;

    static String english = "en";
    static String korean = "ko";

    // 문자열을 문자 단위로 검사하여 한글 문자 또는 영어 문자가 포함되어 있는지 확인
    // 한글 : 0, 영어 : 1, 모두 포함 안되면 : -1
    public static int detectLanguage(String input) {
        boolean containsKorean = false;
        boolean containsEnglish = false;

        for (char ch : input.toCharArray()) {
            if (Character.UnicodeBlock.of(ch) == Character.UnicodeBlock.HANGUL_SYLLABLES ||
                    Character.UnicodeBlock.of(ch) == Character.UnicodeBlock.HANGUL_JAMO ||
                    Character.UnicodeBlock.of(ch) == Character.UnicodeBlock.HANGUL_COMPATIBILITY_JAMO) {
                containsKorean = true;
            } else if (ch >= 'A' && ch <= 'Z' || ch >= 'a' && ch <= 'z') {
                containsEnglish = true;
            }
        }

        if (containsKorean) return 0;
        else if (containsEnglish) return 1;
        else return -1;
    }

    @Override
    public List<IngredientResponseDto> getRandomIngredient(int lan) {
        // 레시피에 포함된 전체 재료 정보 Entity List
        List<Ingredient> ingredientEntityList = ingredientRepository.findRandomIngredient();

        // 반환할 레시피 Dto List
        List<IngredientResponseDto> ingredientResponseDtoList = new ArrayList<>();

        for (Ingredient ingredientEntity : ingredientEntityList) {
            IngredientResponseDto ingredientResponseDto = IngredientResponseDto.of(ingredientEntity);

            // 영문일때
            if (lan == 1) {
                // DB에 한글로 저장되어 있어 영어로 번역해서 response
                ingredientResponseDto.setName(papagoTranslationService.translate(korean, english, ingredientEntity.getName()));
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
                large = papagoTranslationService.translate(korean, english, large);
            }
        }

        return largeList;
    }

    @Override
    public List<String> getMediumList(String large, int lan) {
        List<String> mediumList = ingredientRepository.findMedium(large);

        if (lan == 1) {
            for (String medium : mediumList) {
                medium = papagoTranslationService.translate(korean, english, medium);
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
                name = papagoTranslationService.translate(korean, english, name);
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
