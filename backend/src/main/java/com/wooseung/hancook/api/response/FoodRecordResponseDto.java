package com.wooseung.hancook.api.response;

import com.wooseung.hancook.db.entity.FoodNutrient;
import com.wooseung.hancook.db.entity.FoodRecord;
import com.wooseung.hancook.db.entity.User;
import com.wooseung.hancook.utils.ModelMapperUtils;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FoodRecordResponseDto {
    private Long id;
    private User user;
    private String foodName;
    private int servingSize;
    private String unit;
    private double kcal;
    private double carb;
    private double protein;
    private double fat;
    private double sugar;
    private double cholesterol;
    public static FoodRecordResponseDto of(FoodRecord foodRecordEntity) {
        FoodRecordResponseDto foodRecordResponseDto = ModelMapperUtils.getModelMapper().map(foodRecordEntity, FoodRecordResponseDto.class);
        return foodRecordResponseDto;
    }
}
