package com.wooseung.hancook.db.repository;

import com.wooseung.hancook.db.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    @Query(value = "SELECT * FROM ingredient ORDER BY RAND() limit 3", nativeQuery = true)
    List<Ingredient> findRandomIngredient();

    @Query(value = "SELECT * FROM ingredient WHERE medium LIKE \"%고기%\" ORDER BY RAND() limit 3", nativeQuery = true)
    List<Ingredient> findRandomMeatIngredient();

    @Query(value = "SELECT * FROM ingredient WHERE large LIKE \"%채소%\" ORDER BY RAND() limit 3", nativeQuery = true)
    List<Ingredient> findRandomVegetableIngredient();

    @Query(value = "SELECT DISTINCT i.large FROM ingredient i WHERE i.large IS NOT NULL", nativeQuery = true)
    List<String> findLarge();

    @Query(value = "SELECT DISTINCT i.medium FROM ingredient i WHERE i.large = :large", nativeQuery = true)
    List<String> findMedium(String large);

    @Query(value = "SELECT DISTINCT i.name FROM ingredient i WHERE i.medium = :medium", nativeQuery = true)
    List<String> findName(String medium);

    Optional<Ingredient> findIngredientByName(String name);

    List<Ingredient> findIngredientByMedium(String medium);
    Optional<Ingredient> findByIngredientId(Long id);
    Long findIngredientIdByName(String name);
}
