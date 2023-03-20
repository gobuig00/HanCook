package com.wooseung.hancook.api.response;

import com.wooseung.hancook.db.entity.Process;
import com.wooseung.hancook.utils.ModelMapperUtils;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProcessResponseDto {

    private int processId;
    private int recipeId;
    private int no; // 요리 순서
    private String description;

    public static ProcessResponseDto of(Process processEntity){
        ProcessResponseDto processResponseDto = ModelMapperUtils.getModelMapper().map(processEntity, ProcessResponseDto.class);
        return processResponseDto;
    }

}
