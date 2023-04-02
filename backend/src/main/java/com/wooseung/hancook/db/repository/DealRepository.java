package com.wooseung.hancook.db.repository;

import com.wooseung.hancook.db.entity.Deal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DealRepository extends JpaRepository<Deal, Long> {
    @Query(value = "SELECT DISTINCT d.large FROM deal d", nativeQuery = true)
    List<String> findLarge();

    @Query(value = "SELECT DISTINCT d.medium FROM deal d WHERE d.large = :large", nativeQuery = true)
    List<String> findMedium(String large);

    @Query(value = "SELECT DISTINCT d.small FROM deal d WHERE d.large = :large AND d.medium = :medium", nativeQuery = true)
    List<String> findSmall(String medium, String large);

    @Query(value = "SELECT DISTINCT d.origin FROM deal d WHERE d.large = :large AND d.medium = :medium AND d.small = :small", nativeQuery = true)
    List<String> findOrigin(String large, String medium, String small);

    @Query(value = "SELECT DISTINCT d.deal_date FROM deal d WHERE d.large = :large AND d.medium = :medium AND d.small = :small AND d.origin = :origin ORDER BY d.deal_date DESC LIMIT 7", nativeQuery = true)
    List<String> findDealDateLimit7(String large, String medium, String small, String origin);

    @Query(value = "SELECT DISTINCT d.deal_date FROM deal d WHERE d.small = :name  ORDER BY d.deal_date DESC LIMIT 7", nativeQuery = true)
    List<String> findDealDateLimit7(String name);


    List<Deal> findByLargeAndMediumAndSmallAndOriginAndDealDate(String large, String medium, String small, String origin, String dealDate);

    List<Deal> findBySmallAndDealDate(String medium, String dealDate);
}
