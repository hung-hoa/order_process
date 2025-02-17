package com.example.order_process.security;

import com.example.order_process.model.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

@Getter
public class CustomOAuth2User implements OAuth2User {

    private final User user;
    private final Collection<? extends GrantedAuthority> authorities;

    public CustomOAuth2User(User user, Collection<? extends GrantedAuthority> authorities) {
        this.user = user;
        this.authorities = authorities;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return Map.of(
                "id", user.getFacebookId(),
                "name", user.getFullName(),
                "email", user.getEmail()
        );
    }

    @Override
    public String getName() {
        return user.getFullName();
    }
}
