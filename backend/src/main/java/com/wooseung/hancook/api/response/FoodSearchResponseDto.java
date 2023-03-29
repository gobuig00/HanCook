package com.wooseung.hancook.api.response;

import com.google.gson.annotations.SerializedName;
import com.wooseung.hancook.db.entity.Food;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FoodSearchResponseDto {
    @SerializedName("foods")
    private List<Food> foods;

    public List<Food> getFoods() {
        return foods;
    }

    public void setFoods(List<Food> foods) {
        this.foods = foods;
    }
}
