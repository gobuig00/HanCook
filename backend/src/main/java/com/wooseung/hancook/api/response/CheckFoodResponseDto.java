package com.wooseung.hancook.api.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CheckFoodResponseDto {

    int checkFlag;
    Long id;


}
