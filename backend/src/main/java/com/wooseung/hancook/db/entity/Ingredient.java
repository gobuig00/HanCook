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

    @Column(nullable = false)
    private String large;

    @Column(nullable = false)
    private String medium;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Long ref;

    @OneToMany(mappedBy = "ingredient")
    List<Mart> mart = new ArrayList<>();

    public static Ingredient of(IngredientResponseDto ingredientResponseDto) {
        Ingredient ingredientEntity = ModelMapperUtils.getModelMapper().map(ingredientResponseDto, Ingredient.class);

        return ingredientEntity;
    }

}
