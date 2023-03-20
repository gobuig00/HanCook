package com.wooseung.hancook.api.response;

import com.wooseung.hancook.db.entity.Component;
import com.wooseung.hancook.utils.ModelMapperUtils;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComponentResponseDto {
    private int id;
    private int recipeId;
    private String name;
    private String capacity;
    private long ingredientId;

    public static ComponentResponseDto of(Component componentEntity){
        ComponentResponseDto componentResponseDto = ModelMapperUtils.getModelMapper().map(componentEntity, ComponentResponseDto.class);
        return componentResponseDto;
    }
}
