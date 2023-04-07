package com.wooseung.hancook.api.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserJoinRequestDto {

    private String email;

    private String password;

    private String name;

    private String gender;
}
