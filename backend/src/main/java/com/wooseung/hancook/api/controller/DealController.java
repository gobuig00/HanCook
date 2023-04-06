package com.wooseung.hancook.api.controller;

import com.wooseung.hancook.api.response.DealCheapResponseDto;
import com.wooseung.hancook.api.response.DealResponseDto;
import com.wooseung.hancook.api.service.DealService;
import com.wooseung.hancook.api.service.IngredientService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.StringTokenizer;

@RestController
@RequestMapping("/deal")
@RequiredArgsConstructor

@CrossOrigin(origins = "*", allowedHeaders = "*")
public class DealController {

    private static final Logger logger = LogManager.getLogger(DealController.class);
    private final DealService dealService;
    private final IngredientService ingredientService;

    @GetMapping("/large")
    public ResponseEntity<List<String>> getLarge() {
        List<String> largeList = dealService.getLarge();
        return ResponseEntity.status(HttpStatus.OK).body(largeList);
    }

    @GetMapping("/medium")
    public ResponseEntity<List<String>> getMedium(@RequestParam("large") String large) {
        List<String> mediumList = dealService.getMedium(large);
        return ResponseEntity.status(HttpStatus.OK).body(mediumList);
    }

    @GetMapping("/small")
    public ResponseEntity<List<String>> getSmall(@RequestParam("large") String large, @RequestParam("medium") String medium) {
        List<String> smallList = dealService.getSmall(large, medium);
        return ResponseEntity.status(HttpStatus.OK).body(smallList);
    }

    @GetMapping("/origin")
    public ResponseEntity<List<String>> getOrigin(@RequestParam("large") String large, @RequestParam("medium") String medium, @RequestParam("small") String small) {
        List<String> originList = dealService.getOrigin(large, medium, small);
        return ResponseEntity.status(HttpStatus.OK).body(originList);
    }

    @GetMapping("/")
    public ResponseEntity<List<DealResponseDto>> getDeal(@RequestParam("large") String large, @RequestParam("medium") String medium, @RequestParam("small") String small, @RequestParam("origin") String origin) {
        List<DealResponseDto> dealDtoList = dealService.getDeal(large, medium, small, origin);
        return ResponseEntity.status(HttpStatus.OK).body(dealDtoList);
    }

    //deal을 받아가지고 최대 최소 리스트로 받아가지고 어떤 재료를 입력하면 재료에 대한 리스트
    //를 받아가지고 최대, 최소로 변동 폭 큰 거로
    //db에서 deal값을 받는다.
    @GetMapping("/detail")
    public ResponseEntity<List<DealResponseDto>> getDetailChange(@RequestParam("id") String id) {
        logger.info("id : " + id);
        StringTokenizer st = new StringTokenizer(id, ",");
        id = st.nextToken();
        logger.info("RealIngredientId : " + id);

        String name = ingredientService.searchById(Long.parseLong(id));
        List<DealResponseDto> dealDtoList = dealService.getDetail(name);
        return ResponseEntity.status(HttpStatus.OK).body(dealDtoList);
    }

    //최대 상승한 재료 3개, 최대 하락한 재료 3개
    @GetMapping("/change")
    public ResponseEntity<List<DealResponseDto>> getChange(@RequestParam("lan") int lan) {
        // 오늘 날짜 가져오기
        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);

        // 7일 전 날짜 계산하기
        LocalDate sevenDaysAgo = yesterday.minusDays(7);

        // 날짜 형식 지정
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        // LocalDate 객체를 문자열로 변환하여 저장
        String yesterdayString = yesterday.format(formatter);
        String sevenDaysAgoString = sevenDaysAgo.format(formatter);

//        List<DealCheapResponseDto> dealDtoList = dealService.getChange(yesterdayString, sevenDaysAgoString, lan);
        List<DealResponseDto> dealDtoList = dealService.getChange("20230317", "20230313", lan);

        return ResponseEntity.status(HttpStatus.OK).body(dealDtoList);
    }

    @GetMapping("/Cheap")
    public ResponseEntity<List<DealCheapResponseDto>> getCheap(@RequestParam("lan") int lan) {
        // 오늘 날짜 가져오기
        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);

        // 7일 전 날짜 계산하기
        LocalDate sevenDaysAgo = yesterday.minusDays(7);

        // 날짜 형식 지정
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        // LocalDate 객체를 문자열로 변환하여 저장
        String yesterdayString = yesterday.format(formatter);
        String sevenDaysAgoString = sevenDaysAgo.format(formatter);

//        List<DealCheapResponseDto> dealCheapDtoList = dealService.getCheap(yesterdayString, sevenDaysAgoString, lan);
        List<DealCheapResponseDto> dealCheapDtoList = dealService.getCheap("20230317", "20230313", lan);

        return ResponseEntity.status(HttpStatus.OK).body(dealCheapDtoList);
    }

}