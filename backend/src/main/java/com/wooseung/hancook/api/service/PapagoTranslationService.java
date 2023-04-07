package com.wooseung.hancook.api.service;

public interface PapagoTranslationService {
    String translateKoreanIntoEnglish(String text);

    String translateEnglishIntoKorean(String text);
}
