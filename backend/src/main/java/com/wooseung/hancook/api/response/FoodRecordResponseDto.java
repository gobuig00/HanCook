package com.wooseung.hancook.api.response;

import com.wooseung.hancook.db.entity.FoodRecord;
import com.wooseung.hancook.db.entity.User;
import com.wooseung.hancook.utils.ModelMapperUtils;
import lombok.*;

import java.time.LocalDateTime;

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
    private double salt;
    private double cholesterol;
    private int cnt;
    LocalDateTime eatDate;
    public static FoodRecordResponseDto of(FoodRecord foodRecordEntity) {
        FoodRecordResponseDto foodRecordResponseDto = ModelMapperUtils.getModelMapper().map(foodRecordEntity, FoodRecordResponseDto.class);
        return foodRecordResponseDto;
    }
}
