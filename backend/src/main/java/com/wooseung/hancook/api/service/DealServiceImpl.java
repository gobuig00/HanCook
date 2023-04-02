package com.wooseung.hancook.api.service;

import com.wooseung.hancook.api.response.DealResponseDto;
import com.wooseung.hancook.db.entity.Deal;
import com.wooseung.hancook.db.repository.DealRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service("dealService")
@RequiredArgsConstructor
@Slf4j
public class DealServiceImpl implements DealService{

    private final DealRepository dealRepository;

    @Override
    public List<String> getLarge() {
        List<String> largeList = dealRepository.findLarge();
        return largeList;
    }

    @Override
    public List<String> getMedium(String large) {
        List<String> mediumList = dealRepository.findMedium(large);
        return mediumList;
    }

    @Override
    public List<String> getSmall(String medium, String large) {
        List<String> smallList = dealRepository.findSmall(large, medium);
        return smallList;
    }

    @Override
    public List<String> getOrigin(String large, String medium, String small) {
        List<String> originList = dealRepository.findOrigin(large, medium, small);
        return originList;
    }

    @Override
    public List<DealResponseDto> getDeal(String large, String medium, String small, String origin) {
        List<DealResponseDto> returnList = new ArrayList<>(); // 반환할 리스트
        List<String> dealDateList = dealRepository.findDealDateLimit7(large, medium, small, origin); // 경매날짜 기준 최신순으로 7개 날짜 List

        // 날짜별로 경매 데이터 추출(날짜 내림차순)
        for(int i = dealDateList.size() - 1; i >= 0; i--){
            String dealDate = dealDateList.get(i);
            List<Deal> dealList = dealRepository.findByLargeAndMediumAndSmallAndOriginAndDealDate(large, medium, small, origin, dealDate);

            // 경매 데이터 반올림
            for(Deal deal : dealList){
                deal.setPrice((float) Math.round(deal.getPrice()));
                deal.setMax((float) Math.round(deal.getMax()));
                deal.setMin((float) Math.round(deal.getMin()));
            }
            // 반환할 리스트에 추가
            returnList.addAll(dealList.stream().map(entity -> DealResponseDto.of(entity)).collect(Collectors.toList()));
        }

        return returnList;
    }

    @Override
    public List<DealResponseDto> getDetail(String name){
        List<DealResponseDto> returnList = new ArrayList<>(); // 반환할 리스트
        List<String> dealDateList = dealRepository.findDealDateLimit7(name); // 경매날짜 기준 최신순으로 7개 날짜 List

        // 날짜별로 경매 데이터 추출(날짜 내림차순)
        for(int i = dealDateList.size() - 1; i >= 0; i--){
            String dealDate = dealDateList.get(i);
            List<Deal> dealList = dealRepository.findByMediumAndDealDate(name, dealDate); //name이 medium이랑 똑같은 거임, 즉 재료의 이름

            // 경매 데이터 반올림
            for(Deal deal : dealList){
                deal.setPrice((float) Math.round(deal.getPrice()));
                deal.setMax((float) Math.round(deal.getMax()));
                deal.setMin((float) Math.round(deal.getMin()));
            }
            // 반환할 리스트에 추가
            returnList.addAll(dealList.stream().map(entity -> DealResponseDto.of(entity)).collect(Collectors.toList()));
        }

        return returnList;




    }

}