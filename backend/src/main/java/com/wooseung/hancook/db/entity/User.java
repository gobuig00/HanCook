package com.wooseung.hancook.db.entity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.wooseung.hancook.api.request.UserJoinRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private GenderEnum gender;

    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    private Set<UserRoleEnum> roleSet = new HashSet<>();

    public static User from(UserJoinRequestDto userInfo) {
        return User.builder()
                .email(userInfo.getEmail())
                .password(new BCryptPasswordEncoder().encode(userInfo.getPassword()))
                .name(userInfo.getName())
                .gender(GenderEnum.valueOf(userInfo.getGender()))
                .build();
    }

    public void addUserRole(UserRoleEnum userRole) {
        roleSet.add(userRole);
    }

    public void changePassword(String newPassword) {
        this.password = newPassword;
    }
}
