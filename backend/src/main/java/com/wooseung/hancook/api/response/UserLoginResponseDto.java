package com.wooseung.hancook.api.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserLoginResponseDto {

    private String accessToken;
    private String name;
    private String role;

    public static UserLoginResponseDto from(String accessToken, String name, String role) {
        return UserLoginResponseDto.builder()
                .accessToken(accessToken)
                .name(name)
                .role(role)
                .build();
    }

}
