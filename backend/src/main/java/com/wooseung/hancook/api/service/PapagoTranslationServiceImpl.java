package com.wooseung.hancook.api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@Service("papagoTranslationService")
@RequiredArgsConstructor
@Slf4j
public class PapagoTranslationServiceImpl implements PapagoTranslationService {
    private static final String CLIENT_ID = "";
    private static final String CLIENT_SECRET = "";
    private static final String API_URL = "https://naveropenapi.apigw.ntruss.com/nmt/v1/translation";

    public String translateKoreanIntoEnglish(String text) {
        String sourceLang = "ko";
        String targetLang = "en";

        try {
            text = URLEncoder.encode(text, "UTF-8");
        } catch (IOException e) {
            throw new RuntimeException("인코딩 실패", e);
        }

        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-NCP-APIGW-API-KEY-ID", CLIENT_ID);
        requestHeaders.put("X-NCP-APIGW-API-KEY", CLIENT_SECRET);

        String postParams = "source=" + sourceLang + "&target=" + targetLang + "&text=" + text;

        String responseBody = post(API_URL, requestHeaders, postParams);

        // JSON 객체 파싱
        JSONObject jsonResponse = new JSONObject(responseBody);
        
        return jsonResponse.getJSONObject("message").getJSONObject("result").getString("translatedText");
    }

    public String translateEnglishIntoKorean(String text) {
        String sourceLang = "en";
        String targetLang = "ko";

        try {
            text = URLEncoder.encode(text, "UTF-8");
        } catch (IOException e) {
            throw new RuntimeException("인코딩 실패", e);
        }

        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-NCP-APIGW-API-KEY-ID", CLIENT_ID);
        requestHeaders.put("X-NCP-APIGW-API-KEY", CLIENT_SECRET);

        String postParams = "source=" + sourceLang + "&target=" + targetLang + "&text=" + text;

        String responseBody = post(API_URL, requestHeaders, postParams);

        // JSON 객체 파싱
        JSONObject jsonResponse = new JSONObject(responseBody);

        return jsonResponse.getJSONObject("message").getJSONObject("result").getString("translatedText");
    }

    private static String post(String apiUrl, Map<String, String> requestHeaders, String postParams) {
        HttpURLConnection con = connect(apiUrl);
        try {
            con.setRequestMethod("POST");
            for (Map.Entry<String, String> header : requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }

            con.setDoOutput(true);
            try (OutputStream outputStream = con.getOutputStream()) {
                outputStream.write(postParams.getBytes());
                outputStream.flush();
            }

            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                return readBody(con.getInputStream());
            } else {
                return readBody(con.getErrorStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            con.disconnect();
        }
    }

    private static HttpURLConnection connect(String apiUrl) {
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
        }
    }

    private static String readBody(InputStream body) {
        InputStreamReader streamReader = new InputStreamReader(body);

        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();

            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }

            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는데 실패했습니다.", e);
        }
    }
}

