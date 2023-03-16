package com.wooseung.hancook.api.controller;

import com.wooseung.hancook.api.request.FoodRecordRequestDto;
import com.wooseung.hancook.api.response.BaseResponseBody;
import com.wooseung.hancook.service.FoodRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
public class FoodRecordController {

    private final FoodRecordService foodRecordService;

    @PostMapping("/join")
    public ResponseEntity<BaseResponseBody> join(@RequestBody FoodRecordRequestDto foodRecordRequestDto) {
        System.out.println(foodRecordRequestDto.getFoodName());
        foodRecordService.insertFoodRecord(foodRecordRequestDto);

        return new ResponseEntity<>(new BaseResponseBody("SUCCESS", 201), HttpStatus.CREATED);
    }
}
