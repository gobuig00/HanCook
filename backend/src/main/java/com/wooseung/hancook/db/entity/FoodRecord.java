package com.wooseung.hancook.db.entity;

import com.wooseung.hancook.api.response.FoodRecordResponseDto;
import com.wooseung.hancook.utils.ModelMapperUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class FoodRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "food_record_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false, name = "food_name")
    private String foodName;
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

    @Column(nullable = false, name = "eat_date")
    LocalDateTime eatDate;

    public FoodRecord(User user, String foodName, int servingSize, String unit, double kcal, double carb, double protein, double fat, double sugar, double salt, double cholesterol, LocalDateTime eatDate) {
        this.user = user;
        this.foodName = foodName;
        this.servingSize = servingSize;
        this.unit = unit;
        this.kcal = kcal;
        this.carb = carb;
        this.protein = protein;
        this.fat = fat;
        this.sugar = sugar;
        this.salt = salt;
        this.cholesterol = cholesterol;
        this.eatDate = eatDate;
    }

    public static FoodRecord of(FoodRecordResponseDto foodRecordResponseDto) {
        FoodRecord foodRecordEntity = ModelMapperUtils.getModelMapper().map(foodRecordResponseDto, FoodRecord.class);
        return foodRecordEntity;
    }
}
