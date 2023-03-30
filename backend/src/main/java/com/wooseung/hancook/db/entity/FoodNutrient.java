package com.wooseung.hancook.db.entity;

import com.wooseung.hancook.api.response.FoodNutrientResponseDto;
import com.wooseung.hancook.utils.ModelMapperUtils;
import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class FoodNutrient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "no")
    private Long no;
    private String name;
    @Column(name = "serving_size")
    private int servingSize;
    private String unit;
    private double kcal;
    private double carb;
    private double protein;
    private double fat;
    private double sugar;
    private double salt;
    private double cholesterol;
    public static FoodNutrient of(FoodNutrientResponseDto foodNutrientResponseDto){
        FoodNutrient foodNutrientEntity = ModelMapperUtils.getModelMapper().map(foodNutrientResponseDto, FoodNutrient.class);
        return foodNutrientEntity;
    }

}
