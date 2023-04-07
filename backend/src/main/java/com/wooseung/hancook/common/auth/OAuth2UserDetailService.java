//package com.wooseung.hancook.common.auth;
//
//import com.wooseung.hancook.db.entity.User;
//import com.wooseung.hancook.db.entity.UserRoleEnum;
//import com.wooseung.hancook.db.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.log4j.Log4j2;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
//import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
//import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.HashMap;
//import java.util.Optional;
//import java.util.UUID;
//import java.util.stream.Collectors;
//
//@Log4j2
//@Service
//@RequiredArgsConstructor
//public class OAuth2UserDetailService extends DefaultOAuth2UserService {
//
//    private final UserRepository userRepository;
//
//    @Override
//    @Transactional
//    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
//
//        log.info("------------------------------");
//        log.info("userRequest:" + userRequest);
//
//        String clientName = userRequest.getClientRegistration().getClientName();
//
//        log.info("clientName: " + clientName);
//        log.info(userRequest.getAdditionalParameters());
//
//        OAuth2User oAuth2User = super.loadUser(userRequest);
//
//        log.info("==============================");
//        oAuth2User.getAttributes().forEach((k, v) -> {
//            log.info(k + " : " + v);
//        });
//
//        String email = null;
//        String name = null;
//
//        if (clientName.equals("Google")) {
//            email = oAuth2User.getAttribute("email");
//            name = oAuth2User.getAttribute("name");
//        } else if (clientName.equals("Kakao")) {
//            HashMap<String, String> map = oAuth2User.getAttribute("kakao_account");
//            HashMap<String, String> profile = oAuth2User.getAttribute("properties");
//            email = map.get("email");
//            name = profile.get("nickname");
//        }
//
//        log.info("EMAIL: " + email);
//        log.info("NAME: " + name);
//
//        User user = saveSocialUser(email, name);
//
//        UserDetails ssafyOauth2Member = new UserDetails(
//                user.getEmail(),
//                user.getPassword(),
//                user.getRoleSet()
//                        .stream()
//                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
//                        .collect(Collectors.toList()),
//                oAuth2User.getAttributes()
//        );
//        ssafyOauth2Member.setName(user.getName());
//
//        return ssafyOauth2Member;
//
//    }
//
//    private User saveSocialUser(String email, String name) {
//
//        Optional<User> result = userRepository.findByEmail(email);
//
//        if (result.isPresent()) {
//            return result.get();
//        }
//
//        String password = email + UUID.randomUUID().toString();
//
//        User user = User.builder()
//                .email(email)
//                .name(name)
//                .password(new BCryptPasswordEncoder().encode(password))
//                .build();
//
//        user.addUserRole(UserRoleEnum.USER);
//
//        userRepository.save(user);
//
//        return user;
//    }
//
//}
