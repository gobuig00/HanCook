package com.wooseung.hancook.service;

import com.wooseung.hancook.api.request.UserJoinRequestDto;
import com.wooseung.hancook.api.request.UserLoginRequestDto;
import com.wooseung.hancook.api.response.UserLoginResponseDto;

public interface UserService {
    void join(UserJoinRequestDto joinInfo);

    UserLoginResponseDto login(UserLoginRequestDto loginInfo);

    void emailCheck(String email);
}
