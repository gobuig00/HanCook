package com.wooseung.hancook.api.response;

import com.wooseung.hancook.db.entity.FoodNutrient;
import com.wooseung.hancook.utils.ModelMapperUtils;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FoodNutrientResponseDto {
    private Long no;
    private String name;
    private int servingSize;
    private String unit;
    private double kcal;
    private double carb;
    private double protein;
    private double fat;
    private double sugar;
    private double salt;
    private double cholesterol;

    public static FoodNutrientResponseDto of(FoodNutrient foodNutrientEntity) {
        FoodNutrientResponseDto foodNutrientResponseDto = ModelMapperUtils.getModelMapper().map(foodNutrientEntity, FoodNutrientResponseDto.class);
        return foodNutrientResponseDto;
    }
}
