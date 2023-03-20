package com.wooseung.hancook.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MartResponseDto {


       List<MartPriceResponseDto> homeplus;
       List<MartPriceResponseDto> emart;
       List<MartPriceResponseDto> lotte;

}
