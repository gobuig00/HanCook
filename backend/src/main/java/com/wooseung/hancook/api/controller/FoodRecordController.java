package com.wooseung.hancook.api.controller;

import com.wooseung.hancook.api.request.FoodRecordRequestDto;
import com.wooseung.hancook.api.response.BaseResponseBody;
import com.wooseung.hancook.common.auth.UserDetails;
import com.wooseung.hancook.service.FoodRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/foodrecord")
@RequiredArgsConstructor
public class FoodRecordController {

    private final FoodRecordService foodRecordService;

    @PostMapping
    public ResponseEntity<BaseResponseBody> register(@RequestBody FoodRecordRequestDto foodRecordRequestDto,@AuthenticationPrincipal UserDetails userDetails) {
        foodRecordService.insertFoodRecord(foodRecordRequestDto,userDetails.getEmail());

        return new ResponseEntity<>(new BaseResponseBody("SUCCESS", 201), HttpStatus.CREATED);
    }
}
