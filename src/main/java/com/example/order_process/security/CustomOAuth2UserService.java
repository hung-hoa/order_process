package com.example.order_process.security;

import com.example.order_process.model.User;
import com.example.order_process.model.Role;
import com.example.order_process.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String providerId = oAuth2User.getAttribute("id");
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");

        String username = email != null ? email : providerId;

        Optional<User> existingUser = userRepository.findByEmailOrFacebookId(email, providerId);
        User user;
        if (existingUser.isPresent()) {
            user = existingUser.get();
        } else {
            user = new User();
            user.setFacebookId(providerId);
            user.setEmail(email);
            user.setFullName(name);
            user.setUsername(username);
            user.setRole(Role.USER);
            userRepository.save(user);
        }

        return new CustomOAuth2User(user, Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
    }
}