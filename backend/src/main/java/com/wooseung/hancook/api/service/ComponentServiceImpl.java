package com.wooseung.hancook.api.service;

import com.wooseung.hancook.api.response.ComponentResponseDto;
import com.wooseung.hancook.api.response.IngredientResponseDto;
import com.wooseung.hancook.db.entity.Component;
import com.wooseung.hancook.db.entity.Ingredient;
import com.wooseung.hancook.db.repository.ComponentRepository;
import com.wooseung.hancook.db.repository.IngredientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ComponentServiceImpl implements ComponentService {

    private final ComponentRepository componentRepository;

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
    public List<ComponentResponseDto> getRandomComponent(int lan) {
        // 랜덤 재료 정보 Entity List
        List<Component> componentEntityList = componentRepository.findRandomComponent();

        // 반환할 레시피 Dto List
        List<ComponentResponseDto> componentResponseDtoList = new ArrayList<>();

        for (Component componentEntity : componentEntityList) {
            ComponentResponseDto componentResponseDto = ComponentResponseDto.of(componentEntity);

            // 영문일때
            if (lan == 1) {
                // DB에 한글로 저장되어 있어 영어로 번역해서 response
                componentResponseDto.setName(papagoTranslationService.translate(korean, english, componentEntity.getName()));
            }

            // 반환할 DtoList에 현재 RecipeEntity를 Dto로 변환하여 추가
            componentResponseDtoList.add(componentResponseDto);
        }

        return componentResponseDtoList;
    }

}
