package com.wooseung.hancook.db.entity;

import com.wooseung.hancook.api.response.RecipeResponseDto;
import com.wooseung.hancook.utils.ModelMapperUtils;
import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_id")
    private Long recipeId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String time;

    @Column(nullable = false)
    private String cal;

    @Column(nullable = false)
    private String quantity;

    @Column(nullable = false)
    private String img;

    @Column(name = "youtube_id")
    private String youtubeId;

    public static Recipe of(RecipeResponseDto recipeDto){
        Recipe recipeEntity = ModelMapperUtils.getModelMapper().map(recipeDto, Recipe.class);
        return recipeEntity;
    }
}
