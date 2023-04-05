package com.wooseung.hancook.api.response;

import com.wooseung.hancook.db.entity.Recipe;
import com.wooseung.hancook.utils.ModelMapperUtils;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecipeCardResponseDto {
    private Long recipeId;
    private String name;
    private String description;
    private String time;
    private String cal;
    private String quantity;
    private String img;
    private String youtubeId;
    private String engName;
    private String pronunciation;

    public static RecipeCardResponseDto of(Recipe recipeEntity) {
        RecipeCardResponseDto recipeCardResponseDto = ModelMapperUtils.getModelMapper().map(recipeEntity, RecipeCardResponseDto.class);
        return recipeCardResponseDto;
    }

}
