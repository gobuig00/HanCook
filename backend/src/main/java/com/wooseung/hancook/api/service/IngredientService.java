package com.wooseung.hancook.api.service;

import java.util.List;

public interface IngredientService {

    List<String> getLargeList();

    List<String> getMediumList(String large);

    List<String> getNameList(String medium);

    int searchName(String name);

}
