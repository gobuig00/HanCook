package com.wooseung.hancook.api.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DealCheapResponseDto {
    private String medium;
    private String small;
    private String origin;

    public static DealCheapResponseDto of(Object[] ob){
         DealCheapResponseDto dealCheapResponseDto = new DealCheapResponseDto(String.valueOf(ob[0]), String.valueOf(ob[1]), String.valueOf(ob[2]));
         return dealCheapResponseDto;

    }

}
