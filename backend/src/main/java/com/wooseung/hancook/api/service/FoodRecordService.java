package com.wooseung.hancook.api.service;

import com.wooseung.hancook.api.request.FoodRecordRequestDto;
import com.wooseung.hancook.api.response.FoodRecordResponseDto;
import com.wooseung.hancook.db.entity.User;

import java.util.List;

public interface FoodRecordService {
    void insertFoodRecord(FoodRecordRequestDto foodRecordRequestDto, String email, int cnt);
    List<FoodRecordResponseDto> getFoodRecordById(Long id);

}
