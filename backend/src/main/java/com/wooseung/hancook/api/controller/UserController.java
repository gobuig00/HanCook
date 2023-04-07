package com.wooseung.hancook.api.controller;


import com.wooseung.hancook.api.request.UserJoinRequestDto;
import com.wooseung.hancook.api.request.UserLoginRequestDto;
import com.wooseung.hancook.api.response.BaseResponseBody;
import com.wooseung.hancook.api.response.UserLoginResponseDto;
import com.wooseung.hancook.common.auth.UserDetails;
import com.wooseung.hancook.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @PostMapping("/join")
    public ResponseEntity<BaseResponseBody> join(@RequestBody UserJoinRequestDto joinInfo) {
        //임의로 리턴된 User 인스턴스. 현재 코드는 회원 가입 성공 여부만 판단하기 때문에 굳이 Insert 된 유저 정보를 응답하지 않음.
        userService.join(joinInfo);
        return new ResponseEntity<>(new BaseResponseBody("SUCCESS", 201), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponseDto> login(@RequestBody UserLoginRequestDto loginInfo) {
        return new ResponseEntity<>(userService.login(loginInfo), HttpStatus.OK);
    }

    @GetMapping("/{email}")
    public ResponseEntity<BaseResponseBody> emailCheck(@PathVariable String email) {
        userService.emailCheck(email);
        return new ResponseEntity<>(new BaseResponseBody("SUCCESS", 200), HttpStatus.OK);
    }

    @GetMapping("/test")
    public ResponseEntity<?> test(@AuthenticationPrincipal UserDetails userDetails){
        logger.info(userDetails.getEmail());
        return null;
    }

}
