package com.wooseung.hancook.api.service;


import com.wooseung.hancook.api.request.UserJoinRequestDto;
import com.wooseung.hancook.api.request.UserLoginRequestDto;
import com.wooseung.hancook.api.response.UserLoginResponseDto;
import com.wooseung.hancook.common.exception.ApiException;
import com.wooseung.hancook.common.exception.ExceptionEnum;
import com.wooseung.hancook.common.util.JWTUtil;
import com.wooseung.hancook.db.entity.User;
import com.wooseung.hancook.db.entity.UserRoleEnum;
import com.wooseung.hancook.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

import static com.wooseung.hancook.db.entity.UserRoleEnum.ADMIN;


@Service("userService")
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JWTUtil jwtUtil;


    @Override
    @Transactional
    public void join(UserJoinRequestDto joinInfo) {

        String email = joinInfo.getEmail();
        String name = joinInfo.getName();

        Optional<User> findEmail = userRepository.findByEmail(email);

        if (findEmail.isPresent()) throw new ApiException(ExceptionEnum.MEMBER_EXIST_EXCEPTION);

        User user = User.from(joinInfo);
        userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserLoginResponseDto login(UserLoginRequestDto loginInfo) {
        String email = loginInfo.getEmail();
        String password = loginInfo.getPassword();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ApiException(ExceptionEnum.MEMBER_NOT_EXIST_EXCEPTION));


        Set<UserRoleEnum> roleSet = user.getRoleSet();

        // 로그인 요청한 유저로부터 입력된 패스워드 와 디비에 저장된 유저의 암호화된 패스워드가 같은지 확인.(유효한 패스워드인지 여부 확인)
        if (new BCryptPasswordEncoder().matches(password, user.getPassword())) {
            // 유효한 패스워드가 맞는 경우, 로그인 성공으로 응답.(액세스 토큰을 포함하여 응답값 전달)
            return UserLoginResponseDto.from(jwtUtil.createToken(email), user.getName(), getRole(roleSet));
        }

        // 유효하지 않는 패스워드인 경우, 로그인 실패로 응답.
        throw new ApiException(ExceptionEnum.PASSWORD_NOT_MATCHED_EXCEPTION);
    }

    @Override
    @Transactional(readOnly = true)
    public void emailCheck(String email) {
        Optional<User> findUser = userRepository.findByEmail(email);

        if (findUser.isPresent()) throw new ApiException(ExceptionEnum.MEMBER_EXIST_EXCEPTION);
    }

    private String getRole(Set<UserRoleEnum> roleSet) {
        if (roleSet.contains(ADMIN)) {
            return "ADMIN";
        }
        return "USER";
    }
}
