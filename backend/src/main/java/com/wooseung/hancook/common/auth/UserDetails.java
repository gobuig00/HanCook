package com.wooseung.hancook.common.auth;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

@Log4j2
@Getter
@Setter
@ToString
public class UserDetails extends User {

    private String email;

    private String password;

    private String name;

    private Map<String, Object> attr;

    public UserDetails(String email,
                            String password,
                            Collection<? extends GrantedAuthority> authorities,
                            Map<String, Object> attr) {
        this(email, password, authorities);
        this.attr = attr;
    }

    public UserDetails(String email,
                            String password,
                            Collection<? extends GrantedAuthority> authorities) {

        super(email, password, authorities);
        this.email = email;
        this.password = password;
    }

    //@Override
    public Map<String, Object> getAttributes() {
        return this.attr;
    }

}