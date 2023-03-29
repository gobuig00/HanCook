package com.wooseung.hancook.db.repository;

import com.wooseung.hancook.db.entity.FoodNutrient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodNutrientRepository extends JpaRepository<FoodNutrient, Long> {
    FoodNutrient findByName(String name);
}
