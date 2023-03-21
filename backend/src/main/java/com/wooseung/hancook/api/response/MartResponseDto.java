package com.wooseung.hancook.api.response;

import com.wooseung.hancook.db.entity.Mart;
import com.wooseung.hancook.utils.ModelMapperUtils;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MartResponseDto {

    private Long martId;
    private Long ingredientId;
    private String ingreName;
    private int itemNo;
    private String itemName;
    private String itemPrice;
    private String itemUrl;
    private int mart;

    public static MartResponseDto of(Mart martEntity) {
        MartResponseDto martResponseDto = ModelMapperUtils.getModelMapper().map(martEntity, MartResponseDto.class);

        return martResponseDto;
    }


}
