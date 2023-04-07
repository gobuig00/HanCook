package com.wooseung.hancook.db.entity;

import com.google.gson.annotations.SerializedName;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Food {
    @SerializedName("food_id")
    private long foodId;
    private String name;
    private double calories;
    private double fat;
    private double carbs;
    private double protein;
}
