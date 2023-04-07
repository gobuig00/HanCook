package com.wooseung.hancook.db.repository;

import com.wooseung.hancook.db.entity.FoodNutrient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FoodNutrientRepository extends JpaRepository<FoodNutrient, Long> {
    Optional<FoodNutrient> findById(Long id);
    FoodNutrient findByName(String name);
}
