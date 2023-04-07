package com.wooseung.hancook.api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service("detectLanguageService")
@RequiredArgsConstructor
@Slf4j
public class DetectLanguageServiceImpl implements DetectLanguageService{
    public int detectLanguage(String input) {
        boolean containsKorean = false;
        boolean containsEnglish = false;

        for (char ch : input.toCharArray()) {
            if (Character.UnicodeBlock.of(ch) == Character.UnicodeBlock.HANGUL_SYLLABLES ||
                    Character.UnicodeBlock.of(ch) == Character.UnicodeBlock.HANGUL_JAMO ||
                    Character.UnicodeBlock.of(ch) == Character.UnicodeBlock.HANGUL_COMPATIBILITY_JAMO) {
                containsKorean = true;
            } else if (ch >= 'A' && ch <= 'Z' || ch >= 'a' && ch <= 'z') {
                containsEnglish = true;
            }
        }

        if (containsKorean) return 0;
        else if (containsEnglish) return 1;
        else return -1;
    }
}
