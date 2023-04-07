package com.wooseung.hancook.api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service("cartImageUploadService")
@RequiredArgsConstructor
@Slf4j
public class CartImageUploadServiceImpl implements CartImageUploadService {
    @Value("${imgbb.api.key}")
    private String imgbbApiKey;

    public String uploadImageToImgbb(String base64Image) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://api.imgbb.com/1/upload?key=" + imgbbApiKey;

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("image", base64Image);

        ResponseEntity<Map> response = restTemplate.postForEntity(url, requestBody, Map.class);
        Map<String, Map> data = response.getBody();
        return data.get("data").get("url").toString();
    }
}
