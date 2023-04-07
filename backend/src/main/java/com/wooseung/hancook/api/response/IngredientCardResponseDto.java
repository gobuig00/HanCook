package com.wooseung.hancook.api.response;

import com.wooseung.hancook.db.entity.Ingredient;
import com.wooseung.hancook.utils.ModelMapperUtils;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IngredientCardResponseDto {

    private Long ingredientId;
    private String large;
    private String medium;
    private String name;
    private Long ref;
    private String imageUrl;
    private String engName;
    private String pronunciation;

    public static IngredientCardResponseDto of(Ingredient ingredientEntity) {
        IngredientCardResponseDto ingredientCardResponseDto = ModelMapperUtils.getModelMapper().map(ingredientEntity, IngredientCardResponseDto.class);
        return ingredientCardResponseDto;
    }

}
