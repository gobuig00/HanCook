package com.wooseung.hancook.api.request;

import com.wooseung.hancook.db.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FoodRecordRequestDto {
    private String foodName;
    private int calo;
    private int carbs;
    private int protein;
    private int fat;
    private int salt;
    private int ch;
    private int sugar;
}
