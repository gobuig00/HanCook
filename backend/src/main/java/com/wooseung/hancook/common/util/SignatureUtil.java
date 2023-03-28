package com.wooseung.hancook.common.util;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class SignatureUtil {
    public static String getSignature(String url, String apiKey, String apiSecret) throws Exception {
        List<String> params = new ArrayList<>();
        params.add("oauth_consumer_key=" + apiKey);
        params.add("oauth_nonce=" + System.currentTimeMillis());
        params.add("oauth_signature_method=HMAC-SHA1");
        params.add("oauth_timestamp=" + (System.currentTimeMillis() / 1000));
        Collections.sort(params);

        String paramString = String.join("&", params);
        String signatureBaseString = "GET&" + URLEncoder.encode(url, StandardCharsets.UTF_8.name())
                + "&" + URLEncoder.encode(paramString, StandardCharsets.UTF_8.name());
        SecretKeySpec signingKey = new SecretKeySpec(apiSecret.getBytes(StandardCharsets.UTF_8), "HmacSHA1");
        Mac mac = Mac.getInstance("HmacSHA1");
        mac.init(signingKey);
        byte[] rawHmac = mac.doFinal(signatureBaseString.getBytes(StandardCharsets.UTF_8));
        String signature = new String(Base64.encodeBase64(rawHmac));
        signature = URLEncoder.encode(signature, StandardCharsets.UTF_8.name());
        return signature;
    }
}