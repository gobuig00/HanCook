package com.wooseung.hancook.api.service;

import com.wooseung.hancook.api.response.ComponentResponseDto;

import java.util.List;

public interface ComponentService {

    List<ComponentResponseDto> getRandomComponent(int lan);

}
