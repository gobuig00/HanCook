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

    @Override
    public RecipeResponseDto getRecipeById(Long recipeId) {
        Recipe recipeEntity = recipeRepository.getReferenceById(recipeId);
        RecipeResponseDto recipeDto = RecipeResponseDto.of(recipeEntity);
        return recipeDto;
    }

    @Override
    public List<RecipeResponseDto> getRecipeByIngredient(List<String> ingredient) {
        // 전체 레시피 Entity List
        List<Recipe> recipeEntityList = recipeRepository.findAll();
        // 반환할 레시피 Dto List
        List<RecipeResponseDto> recipeDtoList = new ArrayList<>();
        // 레시피에 포함된 전체 재료 정보 Entity List
        List<Component> componentList = componentRepository.findAll();

        // 정제된 전체 재료 정보 EntityList
        List<Ingredient> ingredientList = ingredientRepository.findAll();
        // ingredientList를 돌면서 ref 값이 존재하는 데이터를 찾는다.
        for(Ingredient ingredientEntity : ingredientList){
            if(ingredientEntity.getRef() != null && ingredientEntity.getRef() > 0){
                // ref 값이 존재하면 componentList를 돌면서 해당 재료의 이름을 ref 값에 해당하는 이름으로 치환한다.
                for(Component component : componentList){
                    if(component.getName().equals(ingredientEntity.getName())){
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
        for(Recipe recipeEntity : recipeEntityList){
            int count = 0;
            HashSet<String> ingredientSet = new HashSet<>();
            for(String ingredientStr : ingredient){
                ingredientSet.add(ingredientStr);
            }
            // 전체 재료 정보 Entity List를 돌면서 레시피 ID가 같고, 선택된 재료들 중 현재 재료 Entity의 이름이 포함되어있는 경우 count++
            for(Component component : componentList){
                if(component.getRecipeId() == recipeEntity.getRecipeId() && ingredientSet.contains(component.getName())){
                    ingredientSet.remove(component.getName());
                    count++;
                }
                // count 값이 ingredient의 크기와 같아지면 선택된 재료들이 모두 포함되었음을 의미
                if(count == ingredient.size()){
                    // 반환할 DtoList에 현재 RecipeEntity를 Dto로 변환하여 추가
                    recipeDtoList.add(RecipeResponseDto.of(recipeEntity));
                    break;
                }
            }
        }

        return recipeDtoList;
    }

    @Override
    public List<ComponentResponseDto> getIngredientByRecipeId(Long recipeId) {
        List<Component> componentEntityList = componentRepository.findAllByRecipeId(recipeId);
        List<ComponentResponseDto> componentDtoList = componentEntityList.stream().map(entity -> ComponentResponseDto.of(entity)).collect(Collectors.toList());

        // component에 이름에 맞는 ingredientId 저장
        for(ComponentResponseDto componentDto : componentDtoList){
            Optional<Ingredient> ingredientEntity = ingredientRepository.findIngredientByName(componentDto.getName());
            if(ingredientEntity.isPresent()){
                componentDto.setIngredientId(ingredientEntity.get().getIngredientId());
            }
        }

        return componentDtoList;
    }

    @Override
    public List<ProcessResponseDto> getProcessByRecipeId(Long recipeId) {
        List<Process> processEntityList = processRepository.findAllByRecipeId(recipeId);
        return processEntityList.stream().map(entity -> ProcessResponseDto.of(entity)).collect(Collectors.toList());
    }
}
