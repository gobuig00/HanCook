package com.wooseung.hancook.api.service;

import com.google.gson.Gson;
import com.wooseung.hancook.api.response.FoodResponseDto;
import com.wooseung.hancook.api.response.FoodSearchResponseDto;
import com.wooseung.hancook.common.util.SignatureUtil;
import com.wooseung.hancook.db.entity.Food;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service("fatSecretService")
@Slf4j
public class FatSecretServiceImpl implements FatSecretService {
    private static final String API_BASE_URL = "https://platform.fatsecret.com/rest/server.api";
    @Value("${fatsecret.apiKey}")
    private String apiKey;
    @Value("${fatsecret.apiSecret}")
    private String apiSecret;
    private final OkHttpClient httpClient = new OkHttpClient();
    private final Gson gson = new Gson();

    public FoodResponseDto getFood(String name) throws Exception {
        FoodSearchResponseDto searchResponse = searchFood(name);
        if (searchResponse.getFoods().isEmpty()) {
            return null;
        }
        long foodId = searchResponse.getFoods().get(0).getFoodId();
        return getFoodById(foodId);
    }

    public FoodSearchResponseDto searchFood(String query) throws Exception {
        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://platform.fatsecret.com/rest/server.api").newBuilder();
        urlBuilder.addQueryParameter("method", "foods.search");
        urlBuilder.addQueryParameter("format", "json");
        urlBuilder.addQueryParameter("search_expression", query);
        urlBuilder.addQueryParameter("oauth_consumer_key", apiKey);
        urlBuilder.addQueryParameter("oauth_nonce", String.valueOf(System.currentTimeMillis())); // oauth_nonce 추가
        urlBuilder.addQueryParameter("oauth_signature_method", "HMAC-SHA1"); // 서명 방법 추가
        urlBuilder.addQueryParameter("oauth_timestamp", String.valueOf(System.currentTimeMillis() / 1000)); // oauth_timestamp 추가

        String signature = SignatureUtil.getSignature(urlBuilder.build().toString(), apiKey, apiSecret);
        urlBuilder.addQueryParameter("oauth_signature", signature);

        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .build();

        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            String json = response.body().string();
            System.out.println(json);
            return gson.fromJson(json, FoodSearchResponseDto.class);
        }
    }

    private FoodResponseDto getFoodById(long foodId) throws Exception {
        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://platform.fatsecret.com/rest/server.api").newBuilder();
        urlBuilder.addQueryParameter("method", "food.get");
        urlBuilder.addQueryParameter("format", "json");
        urlBuilder.addQueryParameter("food_id", String.valueOf(foodId));
        urlBuilder.addQueryParameter("oauth_consumer_key", apiKey);
        urlBuilder.addQueryParameter("oauth_nonce", String.valueOf(System.currentTimeMillis())); // oauth_nonce 추가
        urlBuilder.addQueryParameter("oauth_signature_method", "HMAC-SHA1"); // 서명 방법 추가
        urlBuilder.addQueryParameter("oauth_timestamp", String.valueOf(System.currentTimeMillis() / 1000)); // oauth_timestamp 추가

        String signature = SignatureUtil.getSignature(urlBuilder.build().toString(), apiKey, apiSecret);
        urlBuilder.addQueryParameter("oauth_signature", signature);

        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .build();

        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            String json = response.body().string();
            FoodSearchResponseDto foodSearchResponse = gson.fromJson(json, FoodSearchResponseDto.class);
            Food food = foodSearchResponse.getFoods().get(0);
            return FoodResponseDto.of(food);
        }
    }

}
