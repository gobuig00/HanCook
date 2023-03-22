package com.wooseung.hancook.api.service;

import com.wooseung.hancook.api.response.MartResponseDto;

import java.util.List;

public interface MartService {

    List<MartResponseDto> getMartList(Long ingredientId, int mart);

}
