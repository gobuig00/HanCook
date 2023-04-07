package com.wooseung.hancook.db.entity;

import com.wooseung.hancook.api.response.IngredientResponseDto;
import com.wooseung.hancook.utils.ModelMapperUtils;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ingredient_id")
    private Long ingredientId;

    private String large;

    private String medium;

    private String name;

    private Long ref;

    @Column(name = "image_url")
    private String imageUrl;

//    @OneToMany(mappedBy = "ingredient")
//    List<Mart> mart = new ArrayList<>();

    public static Ingredient of(IngredientResponseDto ingredientResponseDto) {
        Ingredient ingredientEntity = ModelMapperUtils.getModelMapper().map(ingredientResponseDto, Ingredient.class);

        return ingredientEntity;
    }

}
