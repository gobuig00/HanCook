package com.wooseung.hancook.api.response;

import com.wooseung.hancook.db.entity.Ingredient;
import com.wooseung.hancook.utils.ModelMapperUtils;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IngredientResponseDto {

    private Long ingredientId;
    private String large;
    private String medium;
    private String name;
    private Long ref;

    public static IngredientResponseDto of(Ingredient ingredientEntity) {
        IngredientResponseDto ingredientResponseDto = ModelMapperUtils.getModelMapper().map(ingredientEntity, IngredientResponseDto.class);

        return ingredientResponseDto;
    }

}
