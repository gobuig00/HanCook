package com.wooseung.hancook.api.service;

import com.wooseung.hancook.api.request.FoodRecordRequestDto;
import com.wooseung.hancook.api.response.FoodRecordResponseDto;
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

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service("foodRecordService")
@RequiredArgsConstructor
@Slf4j
public class FoodRecordServiceImpl implements FoodRecordService {

    private final FoodRecordRepository foodRecordRepository;
    private final UserRepository userRepository;

    private final PapagoTranslationService papagoTranslationService;

    @Override
    @Transactional
    public void insertFoodRecord(FoodRecordRequestDto foodRecordRequestDto, String email, int cnt) {
        Long no = foodRecordRequestDto.getNo();
        String name = foodRecordRequestDto.getName();
        int servingSize = foodRecordRequestDto.getServingSize();
        String unit = foodRecordRequestDto.getUnit();
        double kcal = foodRecordRequestDto.getKcal();
        double carb = foodRecordRequestDto.getCarb();
        double protein = foodRecordRequestDto.getProtein();
        double fat = foodRecordRequestDto.getFat();
        double sugar = foodRecordRequestDto.getSugar();;
        double salt = foodRecordRequestDto.getSalt();
        double cholesterol = foodRecordRequestDto.getCholesterol();
        // 현재 시간을 가져옵니다.
        LocalDateTime now = LocalDateTime.now();
        // 9시간 뒤의 시간을 계산합니다.
        LocalDateTime nineHoursLater = now.plus(Duration.ofHours(9));

        LocalDateTime date = nineHoursLater;

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ApiException(ExceptionEnum.MEMBER_NOT_EXIST_EXCEPTION));

        FoodRecord foodRecord = new FoodRecord(user, name, servingSize, unit, kcal, carb, protein, fat, sugar, salt, cholesterol, date);
        foodRecord.setFoodName(papagoTranslationService.translateKoreanIntoEnglish(foodRecord.getFoodName()));

        for (int i = 0; i < cnt; i++) {
            foodRecordRepository.save(foodRecord);
        }
    }

    @Override
    public List<FoodRecordResponseDto> getFoodRecordById(Long id) {
        // ID로 찾은 음식 기록 Entity List
        List<FoodRecord> foodRecordEntityList = foodRecordRepository.findAllByUserId(id);
        System.out.println("foodRecordEntityList : " + foodRecordEntityList.size());
        List<FoodRecordResponseDto> foodRecordResponseDtoList = new ArrayList<>();

        for (FoodRecord foodRecordEntity : foodRecordEntityList) {
            FoodRecordResponseDto foodRecordResponseDto = FoodRecordResponseDto.of(foodRecordEntity);
            foodRecordResponseDtoList.add(foodRecordResponseDto);
        }
        System.out.println("foodRecordResponseDtoList : " + foodRecordResponseDtoList.size());
        return foodRecordResponseDtoList;
    }
}
