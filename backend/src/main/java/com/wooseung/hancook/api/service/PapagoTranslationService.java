package com.wooseung.hancook.api.service;

public interface PapagoTranslationService {
    String translate(String sourceLang, String targetLang, String text);
}
