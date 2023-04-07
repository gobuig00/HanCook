package com.wooseung.hancook.db.repository;

import com.wooseung.hancook.db.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

//    long countBy();
//    Page<Recipe> findAll(Pageable pageable);

    // 우선 nativeQuery 사용 후에 데이터 증가하면 페이징 기법으로 최적화
    @Query(value = "SELECT * FROM recipe order by RAND() limit 3", nativeQuery = true)
    List<Recipe> findRandomRecipe();

    List<Recipe> findAllByNameContaining(String name);

    Optional<Recipe> findRecipeByName(String name);

    Long findRecipeIdByName(String name);

}
