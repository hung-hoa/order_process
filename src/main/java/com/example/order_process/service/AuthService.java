package com.example.order_process.service;

import com.example.order_process.model.Role;
import com.example.order_process.model.User;
import com.example.order_process.repository.UserRepository;
import com.example.order_process.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public String register(String username, String password, String email, boolean isOAuthLogin) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("Username already exists!");
        }

        User user;
        if (isOAuthLogin) {
            user = new User(null, username, null, email, null, null, Role.USER);  // Email cần thiết trong OAuth2
        } else {
            user = new User(null, username, passwordEncoder.encode(password), null, null, null, Role.USER);
        }

        userRepository.save(user);
        return "User registered successfully!";
    }

    public String login(String username, boolean isOAuthLogin) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (!isOAuthLogin) {
            throw new IllegalArgumentException("Regular login requires password");
        }

        return jwtTokenProvider.generateToken(user);
    }
}
