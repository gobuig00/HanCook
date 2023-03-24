package com.wooseung.hancook.db.repository;

import com.wooseung.hancook.db.entity.Ingredient;
import com.wooseung.hancook.db.entity.Mart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MartRepository extends JpaRepository<Mart, Long> {

    List<Mart> findAllByIngredientAndMartAndItemNameIsNotNull(Ingredient ingredient, int mart);
}
