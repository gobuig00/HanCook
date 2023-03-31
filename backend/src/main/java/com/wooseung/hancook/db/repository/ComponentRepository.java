package com.wooseung.hancook.db.repository;

import com.wooseung.hancook.db.entity.Component;
import com.wooseung.hancook.db.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ComponentRepository extends JpaRepository<Component, Integer> {
    @Query(value = "SELECT * FROM component ORDER BY RAND() limit 3", nativeQuery = true)
    List<Component> findRandomComponent();
    List<Component> findAllByRecipeId(Long recipeId);
    Component findById(Long id);
}
