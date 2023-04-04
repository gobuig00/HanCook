package com.wooseung.hancook.api.request;

import com.wooseung.hancook.db.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FoodRecordRequestDto {
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
}
