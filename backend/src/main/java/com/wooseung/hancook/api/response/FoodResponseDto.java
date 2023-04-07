package com.wooseung.hancook.api.response;

import com.wooseung.hancook.db.entity.Food;
import com.wooseung.hancook.db.entity.Recipe;
import com.wooseung.hancook.utils.ModelMapperUtils;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FoodResponseDto {

    private String name;
    private double calories;
    private double fat;
    private double carbs;
    private double protein;

    public static FoodResponseDto of(Food food){
        return FoodResponseDto.builder()
                .name(food.getName())
                .calories(food.getCalories())
                .fat(food.getFat())
                .carbs(food.getCarbs())
                .protein(food.getProtein())
                .build();
    }
}
