package com.wooseung.hancook.api.response;

import com.wooseung.hancook.db.entity.Deal;
import com.wooseung.hancook.utils.ModelMapperUtils;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DealResponseDto {
    private Long dealId;
    private String dealDate;
    private String large;
    private String medium;
    private String small;
    private String origin;
    private Float price;
    private Float max;
    private Float min;

    public static DealResponseDto of(Deal dealEntity){
        DealResponseDto dealResponseDto = ModelMapperUtils.getModelMapper().map(dealEntity, DealResponseDto.class);
        return dealResponseDto;
    }
}