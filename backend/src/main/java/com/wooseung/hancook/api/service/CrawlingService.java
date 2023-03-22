package com.wooseung.hancook.api.service;

import com.wooseung.hancook.api.response.CrawlingResponseDto;

public interface CrawlingService {
    CrawlingResponseDto craw(String foodName);
}
