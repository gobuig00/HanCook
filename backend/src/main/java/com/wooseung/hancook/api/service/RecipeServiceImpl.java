package com.wooseung.hancook.api.service;

import com.wooseung.hancook.api.response.ComponentResponseDto;
import com.wooseung.hancook.api.response.ProcessResponseDto;
import com.wooseung.hancook.api.response.RecipeResponseDto;
import com.wooseung.hancook.db.entity.Component;
import com.wooseung.hancook.db.entity.Ingredient;
import com.wooseung.hancook.db.entity.Process;
import com.wooseung.hancook.db.entity.Recipe;
import com.wooseung.hancook.db.repository.ComponentRepository;
import com.wooseung.hancook.db.repository.IngredientRepository;
import com.wooseung.hancook.db.repository.ProcessRepository;
import com.wooseung.hancook.db.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service("recipeService")
@RequiredArgsConstructor
@Slf4j
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final ComponentRepository componentRepository;
    private final ProcessRepository processRepository;
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

    // lan : (한글 : 0) / (영문 : 1)
    @Override
    public List<RecipeResponseDto> getRandomRecipe(int lan) {
        // 레시피에 포함된 전체 재료 정보 Entity List
        List<Recipe> recipeEntityList = recipeRepository.findRandomRecipe();

        // 반환할 레시피 Dto List
        List<RecipeResponseDto> recipeResponseDtoList = new ArrayList<>();

        for (Recipe recipeEntity : recipeEntityList) {
            RecipeResponseDto recipeResponseDto = RecipeResponseDto.of(recipeEntity);

            // 영문일때
            if (lan == 1) {
                // DB에 한글로 저장되어 있어 영어로 번역해서 response
                recipeResponseDto.setName(papagoTranslationService.translate(korean, english, recipeEntity.getName()));
                recipeResponseDto.setDescription(papagoTranslationService.translate(korean, english, recipeEntity.getDescription()));
            }

            // 반환할 DtoList에 현재 RecipeEntity를 Dto로 변환하여 추가
            recipeResponseDtoList.add(recipeResponseDto);
        }

        return recipeResponseDtoList;
    }

    // lan : (한글 : 0) / (영문 : 1)
    @Override
    public RecipeResponseDto getRecipeById(Long recipeId, int lan) {
        Recipe recipeEntity = recipeRepository.getReferenceById(recipeId);
        RecipeResponseDto recipeResponseDto = RecipeResponseDto.of(recipeEntity);

        // 영문일때
        if (lan == 1) {
            // DB에 한글로 저장되어 있어 영어로 번역해서 response
            recipeResponseDto.setName(papagoTranslationService.translate(korean, english, recipeEntity.getName()));
            recipeResponseDto.setDescription(papagoTranslationService.translate(korean, english, recipeEntity.getDescription()));
        }

        return recipeResponseDto;
    }

    // lan : (한글 : 0) / (영문 : 1)
    // 이름으로 검색해서 레시피 목록 가져오기
    @Override
    public List<RecipeResponseDto> getRecipeByName(String name, int lan) {
        int flag = detectLanguage(name);

        // 입력받은 이름이 영어라면 한글로 변환
        if (flag == 1) name = papagoTranslationService.translate(english, korean, name);

        // 이름으로 찾은 레시피 Entity List
        List<Recipe> recipeEntityList = recipeRepository.findAllByNameContaining(name);

        // 영문일때
        if (lan == 1) {
            for (Recipe recipeEntity : recipeEntityList) {
                recipeEntity.setName(papagoTranslationService.translate(korean, english, recipeEntity.getName()));
                recipeEntity.setDescription(papagoTranslationService.translate(korean, english, recipeEntity.getDescription()));
            }
        }

        return recipeEntityList.stream()
                .map(entity -> RecipeResponseDto.of(entity))
                .collect(Collectors.toList());
    }

    // lan : (한글 : 0) / (영문 : 1)
    @Override
    public List<RecipeResponseDto> getRecipeByIngredient(List<String> ingredient, int lan) {
        // 전체 레시피 Entity List
        List<Recipe> recipeEntityList = recipeRepository.findAll();
        // 반환할 레시피 Dto List
        List<RecipeResponseDto> recipeResponseDtoList = new ArrayList<>();
        // 레시피에 포함된 전체 재료 정보 Entity List
        List<Component> componentList = componentRepository.findAll();

        // 정제된 전체 재료 정보 EntityList
        List<Ingredient> ingredientList = ingredientRepository.findAll();
        // ingredientList를 돌면서 ref 값이 존재하는 데이터를 찾는다.
        for (Ingredient ingredientEntity : ingredientList) {
            if (ingredientEntity.getRef() != null && ingredientEntity.getRef() > 0) {
                // ref 값이 존재하면 componentList를 돌면서 해당 재료의 이름을 ref 값에 해당하는 이름으로 치환한다.
                for (Component component : componentList) {
                    if (component.getName().equals(ingredientEntity.getName())) {
                        component.setName(ingredientList.get((int) (ingredientEntity.getRef() - 1)).getName());
                    }
                }
            }
        }

        /*
         * 전체 레시피 Entity List를 돌면서 해당 레시피 ID를 가진 재료 Entity만을 DB에서 추출할 수 있지만,
         * 500개의 레시피 Entity List를 돌면서 매번 select문을 수행하니 동작이 느려서 미리 전체 재료 Entity List를 추출하고
         * 반복문을 통해 검사하는 방법을 사용
         */
        // 전체 레시피 Entity List를 돌면서 선택된 재료들(ingredient)가 모두 포함된 레시피를 찾아 recipeDtoList에 추가
        for (Recipe recipeEntity : recipeEntityList) {
            int count = 0;
            HashSet<String> ingredientSet = new HashSet<>();
            for (String ingredientStr : ingredient) {
                int flag = detectLanguage(ingredientStr);

                // 입력받은 재료가 영어라면 한글로 변환
                if (flag == 1) ingredientStr = papagoTranslationService.translate(english, korean, ingredientStr);

                ingredientSet.add(ingredientStr);
            }
            // 전체 재료 정보 Entity List를 돌면서 레시피 ID가 같고, 선택된 재료들 중 현재 재료 Entity의 이름이 포함되어있는 경우 count++
            for (Component component : componentList) {
                if (component.getRecipeId() == recipeEntity.getRecipeId() && ingredientSet.contains(component.getName())) {
                    ingredientSet.remove(component.getName());
                    count++;
                }
                // count 값이 ingredient의 크기와 같아지면 선택된 재료들이 모두 포함되었음을 의미
                if (count == ingredient.size()) {
                    RecipeResponseDto recipeResponseDto = RecipeResponseDto.of(recipeEntity);

                    // 영문일때
                    if (lan == 1) {
                        // DB에 한글로 저장되어 있어 영어로 번역해서 response
                        recipeResponseDto.setName(papagoTranslationService.translate(korean, english, recipeEntity.getName()));
                        recipeResponseDto.setDescription(papagoTranslationService.translate(korean, english, recipeEntity.getDescription()));
                    }

                    // 반환할 DtoList에 현재 RecipeEntity를 Dto로 변환하여 추가
                    recipeResponseDtoList.add(recipeResponseDto);
                    break;
                }
            }
        }

        return recipeResponseDtoList;
    }

    // lan : (한글 : 0) / (영문 : 1)
    @Override
    public List<ComponentResponseDto> getIngredientByRecipeId(Long recipeId, int lan) {
        List<Component> componentEntityList = componentRepository.findAllByRecipeId(recipeId);

        List<ComponentResponseDto> componentResponseDtoList = componentEntityList.stream().map(entity -> ComponentResponseDto.of(entity)).collect(Collectors.toList());

        // component에 이름에 맞는 ingredientId 저장
        for (ComponentResponseDto componentResponseDto : componentResponseDtoList) {
            Optional<Ingredient> ingredientEntity = ingredientRepository.findIngredientByName(componentResponseDto.getName());
            if (ingredientEntity.isPresent()) {
                componentResponseDto.setIngredientId(ingredientEntity.get().getIngredientId());

                // 영문일때
                if (lan == 1) {
                    componentResponseDto.setName(papagoTranslationService.translate(korean, english, componentResponseDto.getName()));
                    componentResponseDto.setCapacity(papagoTranslationService.translate(korean, english, componentResponseDto.getCapacity()));
                }
            }
        }

        return componentResponseDtoList;
    }

    // lan : (한글 : 0) / (영문 : 1)
    @Override
    public List<ProcessResponseDto> getProcessByRecipeId(Long recipeId, int lan) {
        List<Process> processEntityList = processRepository.findAllByRecipeId(recipeId);

        // 영문일때
        if (lan == 1) {
            for (Process processEntity : processEntityList) {
                processEntity.setDescription(papagoTranslationService.translate(korean, english, processEntity.getDescription()));
            }
        }

        return processEntityList.stream().map(entity -> ProcessResponseDto.of(entity)).collect(Collectors.toList());
    }

    @Override
    public int searchName(String name) {
        Optional<Recipe> recipe = recipeRepository.findRecipeByName(name);

        if (recipe.isPresent()) return 1;
        else return 0;
    }

}
