package com.wooseung.hancook.db.repository;

import com.wooseung.hancook.db.entity.FoodRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodRecordRepository extends JpaRepository<FoodRecord, Long> {
    List<FoodRecord> findAllByUserId(Long id);
}
