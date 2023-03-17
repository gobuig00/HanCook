package com.wooseung.hancook.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponseBody {

    String message = null;

    Integer statusCode = null;

}