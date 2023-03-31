package com.wooseung.hancook.db.repository;

import com.wooseung.hancook.db.entity.Cart;
import com.wooseung.hancook.db.entity.FoodRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    List<FoodRecord> findAllByUserId(Long id);
}
