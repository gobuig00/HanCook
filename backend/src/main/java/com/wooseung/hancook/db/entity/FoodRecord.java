package com.wooseung.hancook.db.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class FoodRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "food_record_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false, name = "food_name")
    private String foodName;

    @Column(nullable = false)
    private int calo;

    @Column(nullable = false)
    private int carbs;

    @Column(nullable = false)
    private int protein;

    @Column(nullable = false)
    private int fat;

    @Column(nullable = false)
    private int salt;

    @Column(nullable = false)
    private int ch;

    @Column(nullable = false)
    private int sugar;

    @Column(nullable = false, name = "food_date")
    LocalDateTime foodDate;

    public FoodRecord(User user, String foodName, int calo, int carbs, int protein, int fat, int salt, int ch, int sugar, LocalDateTime foodDate) {
        this.user = user;
        this.foodName = foodName;
        this.calo = calo;
        this.carbs = carbs;
        this.protein = protein;
        this.fat = fat;
        this.salt = salt;
        this.ch = ch;
        this.sugar = sugar;
        this.foodDate = foodDate;
    }

    public FoodRecord(User user, String foodName, int calo, int carbs, int protein, int fat, int salt, int ch, int sugar) {

    }
}
