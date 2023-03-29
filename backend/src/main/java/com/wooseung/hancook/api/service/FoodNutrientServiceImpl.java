package com.wooseung.hancook.api.service;

import com.wooseung.hancook.api.response.FoodNutrientResponseDto;
import com.wooseung.hancook.db.entity.FoodNutrient;
import com.wooseung.hancook.db.repository.FoodNutrientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service("foodNutrientServiceService")
@RequiredArgsConstructor
@Slf4j
public class FoodNutrientServiceImpl implements FoodNutrientService {

    private final FoodNutrientRepository foodNutrientRepository;

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
    public FoodNutrientResponseDto getNutrientByName(String name) {
        int flag = detectLanguage(name);

        // 입력받은 이름이 영어라면 한글로 변환
        if (flag == 1) name = papagoTranslationService.translate(english, korean, name);

        // 이름으로 찾은 레시피 Entity List
        FoodNutrient foodNutrientEntity = foodNutrientRepository.findByName(name);
        FoodNutrientResponseDto foodNutrientResponseDto = FoodNutrientResponseDto.of(foodNutrientEntity);

        return foodNutrientResponseDto;
    }

}
