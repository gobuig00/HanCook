package com.wooseung.hancook.db.entity;

import com.wooseung.hancook.api.response.MartResponseDto;
import com.wooseung.hancook.utils.ModelMapperUtils;
import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Mart {
    @Id
    @Column(name = "mart_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long martId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ingredientId")
    private Ingredient ingredient;

    @Column(nullable = false, name = "ingredient_name")
    private String ingreName;

    @Column(nullable = false, name = "item_no")
    private int itemNo;

    @Column(nullable = false, name = "item_name")
    private String itemName;

    @Column(nullable = false, name = "item_price")
    private String itemPrice;

    @Column(nullable = false, name = "item_url")
    private String itemUrl;

    @Column(nullable = false)
    private int mart;

    public static Mart of(MartResponseDto martResponseDto) {
        Mart martEntity = ModelMapperUtils.getModelMapper().map(martResponseDto, Mart.class);
        return martEntity;
    }
    
}
