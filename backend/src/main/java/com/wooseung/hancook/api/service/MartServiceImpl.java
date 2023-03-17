package com.wooseung.hancook.api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service("recipeService")
@RequiredArgsConstructor
@Slf4j
public class MartServiceImpl implements MartService {


    @Override
    @Transactional
    public void craw(String foodName) {

    }
}
