package com.wooseung.hancook.service;

import com.wooseung.hancook.api.request.FoodRecordRequestDto;
import com.wooseung.hancook.db.entity.FoodRecord;
import com.wooseung.hancook.db.entity.User;
import com.wooseung.hancook.db.repository.FoodRecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static com.wooseung.hancook.db.entity.GenderEnum.MAN;

@Service("foodRecordService")
@RequiredArgsConstructor
@Slf4j
public class FoodRecordServiceImpl implements FoodRecordService {

    private final FoodRecordRepository foodRecordRepository;

    @Override
    @Transactional
    public void insertFoodRecord(FoodRecordRequestDto foodRecordRequestDto) {
        String foodName = foodRecordRequestDto.getFoodName();
        int calo = foodRecordRequestDto.getCalo();
        int carbs = foodRecordRequestDto.getCarbs();
        int protein = foodRecordRequestDto.getProtein();
        int fat = foodRecordRequestDto.getFat();
        int salt = foodRecordRequestDto.getSalt();
        int ch = foodRecordRequestDto.getCh();
        int sugar = foodRecordRequestDto.getSugar();

        User user = new User(1L, "ssafy@ssafy.com", "sasfy1234", "ssafy", MAN);

        FoodRecord foodRecord = new FoodRecord(user, foodName, calo, carbs, protein, fat, salt, ch, sugar);

        foodRecordRepository.save(foodRecord);
    }
}
