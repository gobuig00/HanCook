package com.wooseung.hancook.api.controller;

import com.wooseung.hancook.api.response.MartResponseDto;
import com.wooseung.hancook.api.service.MartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mart")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MartController {

    private final MartService martService;

    // 재료 아이디(ingredientId)와 마트 종류(mart, 0:이마트몰 1:홈플러스)을 입력하면 마트 정보를 반환
    @GetMapping
    public ResponseEntity<List<MartResponseDto>> getMart(@RequestParam("ingredientId") Long ingredientId, @RequestParam("mart") int mart) {
        List<MartResponseDto> martList = martService.getMartList(ingredientId, mart);
        return ResponseEntity.status(HttpStatus.OK).body(martList);
    }
}
