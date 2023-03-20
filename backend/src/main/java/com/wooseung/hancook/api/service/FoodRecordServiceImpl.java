package com.wooseung.hancook.api.service;

import com.wooseung.hancook.api.request.FoodRecordRequestDto;
import com.wooseung.hancook.common.exception.ApiException;
import com.wooseung.hancook.common.exception.ExceptionEnum;
import com.wooseung.hancook.db.entity.FoodRecord;
import com.wooseung.hancook.db.entity.User;
import com.wooseung.hancook.db.repository.FoodRecordRepository;
import com.wooseung.hancook.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.time.LocalDateTime;

@Service("foodRecordService")
@RequiredArgsConstructor
@Slf4j
public class FoodRecordServiceImpl implements FoodRecordService {

    private final FoodRecordRepository foodRecordRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void insertFoodRecord(FoodRecordRequestDto foodRecordRequestDto, String email) {
        String foodName = foodRecordRequestDto.getFoodName();
        int calo = foodRecordRequestDto.getCalo();
        int carbs = foodRecordRequestDto.getCarbs();
        int protein = foodRecordRequestDto.getProtein();
        int fat = foodRecordRequestDto.getFat();
        int salt = foodRecordRequestDto.getSalt();
        int ch = foodRecordRequestDto.getCh();
        int sugar = foodRecordRequestDto.getSugar();
        LocalDateTime date = LocalDateTime.now();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ApiException(ExceptionEnum.MEMBER_NOT_EXIST_EXCEPTION));
        FoodRecord foodRecord = new FoodRecord(user, foodName, calo, carbs, protein, fat, salt, ch, sugar, date);
        foodRecordRepository.save(foodRecord);
    }
}
