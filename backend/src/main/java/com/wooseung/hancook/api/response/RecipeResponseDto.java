package com.wooseung.hancook.api.response;

import com.wooseung.hancook.db.entity.Recipe;
import com.wooseung.hancook.utils.ModelMapperUtils;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecipeResponseDto {
    private int recipeId;
    private String name;
    private String description;
    private String time;
    private String cal;
    private String quantity;
    private String img;

    public static RecipeResponseDto of(Recipe recipeEntity){
        RecipeResponseDto recipeDto = ModelMapperUtils.getModelMapper().map(recipeEntity, RecipeResponseDto.class);
        return recipeDto;
    }
}
