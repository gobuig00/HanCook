package com.wooseung.hancook.api.response;

import com.wooseung.hancook.db.entity.Recipe;
import com.wooseung.hancook.utils.ModelMapperUtils;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecipeResponseDto {
    private Long recipeId;
    private String name;
    private String description;
    private String time;
    private String cal;
    private String quantity;
    private String img;
    private String youtubeId;

    public static RecipeResponseDto of(Recipe recipeEntity){
        RecipeResponseDto recipeResponseDto = ModelMapperUtils.getModelMapper().map(recipeEntity, RecipeResponseDto.class);
        return recipeResponseDto;
    }
}
