package com.wooseung.hancook.api.service;

import com.wooseung.hancook.api.response.DealCheapResponseDto;
import com.wooseung.hancook.api.response.DealResponseDto;
import com.wooseung.hancook.db.entity.Deal;
import com.wooseung.hancook.db.repository.DealRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.Converters;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service("dealService")
@RequiredArgsConstructor
@Slf4j
public class DealServiceImpl implements DealService {

    private final DealRepository dealRepository;

    private final PapagoTranslationService papagoTranslationService;

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
        for (int i = dealDateList.size() - 1; i >= 0; i--) {
            String dealDate = dealDateList.get(i);
            List<Deal> dealList = dealRepository.findByLargeAndMediumAndSmallAndOriginAndDealDate(large, medium, small, origin, dealDate);

            // 경매 데이터 반올림
            for (Deal deal : dealList) {
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
    public List<DealResponseDto> getDetail(String name) {
        List<DealResponseDto> returnList = new ArrayList<>(); // 반환할 리스트
        List<String> dealDateList = dealRepository.findDealDateLimit7(name); // 경매날짜 기준 최신순으로 7개 날짜 List
        // 날짜별로 경매 데이터 추출(날짜 내림차순)
        for (int i = dealDateList.size() - 1; i >= 0; i--) {
            String dealDate = dealDateList.get(i);
            List<Deal> dealList = dealRepository.findBySmallAndDealDate(name, dealDate); //name이 medium이랑 똑같은 거임, 즉 재료의 이름

            // 경매 데이터 반올림
            for (Deal deal : dealList) {
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
    public List<DealResponseDto> getChange(String today, String sevenDaysAgo, int lan) {
        List<DealResponseDto> returnList = new ArrayList<>(); // 반환할 리스트
        List<Object[]> dealMaxList = dealRepository.findMax(today, sevenDaysAgo); // 7일전 부터 오늘까지 최대 증가율 3개 deal

        for (Object[] ob : dealMaxList) {
            String medium = String.valueOf(ob[0]);
            String small = String.valueOf(ob[1]);
            String origin = String.valueOf(ob[2]);

            List<Deal> dealDateList = dealRepository.findDealsByMediumAndSmallAndOriginAndDateRange(medium, small, origin, sevenDaysAgo, today); // medium, small, origin 이 같은 7일전까지의 데이터를 가져온다.

            if (lan == 1) {
                for (Deal dealEntity : dealDateList) {
                    dealEntity.setMedium(papagoTranslationService.translateKoreanIntoEnglish(dealEntity.getMedium()));
                    dealEntity.setSmall(papagoTranslationService.translateKoreanIntoEnglish(dealEntity.getSmall()));
                }
            }

            returnList.addAll(dealDateList.stream().map(entity -> DealResponseDto.of(entity)).collect(Collectors.toList()));
        }

        List<Object[]> dealMinList = dealRepository.findMin(today, sevenDaysAgo);
        for (Object[] ob : dealMinList) {
            String medium = String.valueOf(ob[0]);
            String small = String.valueOf(ob[1]);
            String origin = String.valueOf(ob[2]);

            List<Deal> dealDateList = dealRepository.findDealsByMediumAndSmallAndOriginAndDateRange(medium, small, origin, sevenDaysAgo, today); // medium, small, origin 이 같은 7일전까지의 데이터를 가져온다.

            if (lan == 1) {
                for (Deal dealEntity : dealDateList) {
                    dealEntity.setMedium(papagoTranslationService.translateKoreanIntoEnglish(dealEntity.getMedium()));
                    dealEntity.setSmall(papagoTranslationService.translateKoreanIntoEnglish(dealEntity.getSmall()));
                }
            }

            returnList.addAll(dealDateList.stream().map(entity -> DealResponseDto.of(entity)).collect(Collectors.toList()));
        }

        return returnList;
    }

    @Override
    public List<DealCheapResponseDto> getCheap(String today, String sevenDaysAgo, int lan) {
        List<DealCheapResponseDto> returnList = new ArrayList<>(); // 반환할 리스트
        List<Object[]> dealMinList = dealRepository.findMin(today, sevenDaysAgo); // 7일전 부터 오늘까지 최대 증가율 3개 deal

        for (Object[] deal : dealMinList) {
            DealCheapResponseDto dealCheapResponseDto = new DealCheapResponseDto(String.valueOf(deal[0]), String.valueOf(deal[1]), String.valueOf(deal[2]));
            if (lan == 1) {
                dealCheapResponseDto.setMedium(papagoTranslationService.translateKoreanIntoEnglish(dealCheapResponseDto.getMedium()));
                dealCheapResponseDto.setSmall(papagoTranslationService.translateKoreanIntoEnglish(dealCheapResponseDto.getSmall()));
            }
            returnList.add(dealCheapResponseDto);
        }
        return returnList;
    }

}