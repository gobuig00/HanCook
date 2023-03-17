package com.wooseung.hancook.api.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FoodRecognitionService {
    String recognizeFood(MultipartFile image) throws IOException;

}
