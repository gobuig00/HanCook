package com.wooseung.hancook.db.repository;

import com.wooseung.hancook.db.entity.Deal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

    @Query(value = "SELECT t1.medium, t1.small, t1.origin, MIN(t1.price) as min_price, MAX(t2.price) as max_price, ((MAX(t2.price) - MIN(t1.price)) / MIN(t1.price) * 100) as price_difference_percentage FROM (SELECT * FROM deal WHERE deal_date = :sevenDaysAgo) AS t1 JOIN (SELECT * FROM deal WHERE deal_date = :today) AS t2 ON t1.large = t2.large AND t1.medium = t2.medium AND t1.small = t2.small AND t1.origin = t2.origin GROUP BY t1.large, t1.medium, t1.small, t1.origin ORDER BY price_difference_percentage DESC LIMIT 3", nativeQuery = true)
    List<Object[]> findMax(String today, String sevenDaysAgo);

    @Query(value = "SELECT * FROM deal d WHERE d.medium = :medium AND d.small = :small AND d.origin = :origin AND d.deal_date BETWEEN :startDate AND :endDate order by d.deal_date ASC", nativeQuery = true)
    List<Deal> findDealsByMediumAndSmallAndOriginAndDateRange(String medium, String small, String origin, String startDate, String endDate);

    @Query(value = "SELECT t1.medium, t1.small, t1.origin, MIN(t1.price) as min_price, MAX(t2.price) as max_price, ((MAX(t2.price) - MIN(t1.price)) / MIN(t1.price) * 100) as price_difference_percentage FROM (SELECT * FROM deal WHERE deal_date = :sevenDaysAgo) AS t1 JOIN (SELECT * FROM deal WHERE deal_date = :today) AS t2 ON t1.large = t2.large AND t1.medium = t2.medium AND t1.small = t2.small AND t1.origin = t2.origin GROUP BY t1.large, t1.medium, t1.small, t1.origin ORDER BY price_difference_percentage ASC LIMIT 3", nativeQuery = true)
    List<Object[]> findMin(String today, String sevenDaysAgo);

    List<Deal> findByLargeAndMediumAndSmallAndOriginAndDealDate(String large, String medium, String small, String origin, String dealDate);

    List<Deal> findBySmallAndDealDate(String medium, String dealDate);



}
