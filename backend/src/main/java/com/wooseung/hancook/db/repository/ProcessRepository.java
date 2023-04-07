package com.wooseung.hancook.db.repository;

import com.wooseung.hancook.db.entity.Process;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProcessRepository extends JpaRepository<Process, Long> {

    List<Process> findAllByRecipeId(Long recipeId);
}
