package com.wooseung.hancook.db.repository;

import com.wooseung.hancook.db.entity.Component;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComponentRepository extends JpaRepository<Component, Integer> {
    List<Component> findAllByRecipeId(Long recipeId);
}
