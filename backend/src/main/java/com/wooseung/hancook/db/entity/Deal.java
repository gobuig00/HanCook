package com.wooseung.hancook.db.entity;

import com.wooseung.hancook.api.response.DealResponseDto;
import com.wooseung.hancook.utils.ModelMapperUtils;
import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Deal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "deal_id")
    private Long id;

    @Column(nullable = false, name = "deal_date")
    private String dealDate;

    @Column(nullable = false)
    private String large;

    @Column(nullable = false)
    private String medium;

    @Column(nullable = false)
    private String small;

    @Column(nullable = false)
    private String origin;

    @Column(nullable = false)
    private Float price;

    @Column(nullable = false)
    private Float max;

    @Column(nullable = false)
    private Float min;

    public static Deal of(DealResponseDto dealResponseDto){
        Deal dealEntity = ModelMapperUtils.getModelMapper().map(dealResponseDto, Deal.class);
        return dealEntity;
    }
}
