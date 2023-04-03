package com.wooseung.hancook.api.service;

import com.wooseung.hancook.api.response.DealCheapResponseDto;
import com.wooseung.hancook.api.response.DealResponseDto;

import java.util.List;

public interface DealService {
    List<DealResponseDto> getDeal(String large, String medium, String small, String origin);

    List<String> getLarge();

    List<String> getMedium(String large);

    List<String> getSmall(String medium, String large);

    List<String> getOrigin(String large, String medium, String small);
    List<DealResponseDto> getDetail(String name);
    List<DealResponseDto> getChange(String today, String sevenDaysAgo);
    List<DealCheapResponseDto> getCheap(String today, String sevenDaysAgo, int lan);
}