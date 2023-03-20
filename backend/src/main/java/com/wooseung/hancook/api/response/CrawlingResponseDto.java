package com.wooseung.hancook.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CrawlingResponseDto {


       List<CrawlingPriceResponseDto> homeplus;
       List<CrawlingPriceResponseDto> emart;
       List<CrawlingPriceResponseDto> lotte;

}
