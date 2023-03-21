package com.wooseung.hancook.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CrawlingResponseDto {


       List<CrawlingPriceResponseDto> homeplus;
       List<CrawlingPriceResponseDto> emart;
       List<CrawlingPriceResponseDto> lotte;

}
