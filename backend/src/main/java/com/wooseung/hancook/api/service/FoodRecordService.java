package com.wooseung.hancook.api.service;

import com.wooseung.hancook.api.request.FoodRecordRequestDto;
import com.wooseung.hancook.db.entity.User;

public interface FoodRecordService {
    void insertFoodRecord(FoodRecordRequestDto foodRecordRequestDto, String email);

}
