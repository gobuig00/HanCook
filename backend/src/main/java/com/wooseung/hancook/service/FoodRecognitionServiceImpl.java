package com.wooseung.hancook.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service("foodRecognitionService")
@Slf4j
public class FoodRecognitionServiceImpl implements FoodRecognitionService {

    private final RestTemplate restTemplate;
    private final String apiEndpointUrl = "https://18c2a61d-b6aa-4581-b1df-02f9c7337ce0.api.kr-central-1.kakaoi.io/ai/vision/4906cb06cc5f42858a61ccbf10bd0f74";
    private final String apiKey = "cc044e06145b8c458fba2fb91ed7629f";

    public FoodRecognitionServiceImpl(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public String recognizeFood(MultipartFile image) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.set("x-api-key", apiKey);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        ByteArrayResource contentsAsResource = new ByteArrayResource(image.getBytes()) {
            @Override
            public String getFilename() {
                return image.getOriginalFilename();
            }
        };
        body.add("image", contentsAsResource);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(apiEndpointUrl, HttpMethod.POST, requestEntity, String.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return responseEntity.getBody();
        } else {
            throw new IOException("Recognition failed with status code: " + responseEntity.getStatusCodeValue());
        }
    }
}