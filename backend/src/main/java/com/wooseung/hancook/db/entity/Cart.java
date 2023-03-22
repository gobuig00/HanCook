//package com.wooseung.hancook.db.entity;
//
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//import javax.persistence.*;
//
//@Entity
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//@Getter
//public class Cart {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "cart_id")
//    private Long id;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
//    private User user;
//
//    @Column(nullable = false, name = "ingredient_name")
//    private String ingredientName;
//
//    @Column(nullable = false, name = "item_no")
//    private Long itemNo;
//
//    @Column(nullable = false, name = "item_name")
//    private String itemName;
//
//    @Column(nullable = false, name = "item_url")
//    private String itemUrl;
//
//    @Enumerated(EnumType.STRING)
//    private MartEnum martEnum;
//
//
//}
