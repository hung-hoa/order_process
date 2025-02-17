package com.example.order_process.controller;

import com.example.order_process.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public String register(@RequestParam String username,
                           @RequestParam String password,
                           @RequestParam(required = false) String email,
                           @RequestParam boolean isOAuthLogin) {
        return authService.register(username, password, email, isOAuthLogin);
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam(required = false) String password,
                        @RequestParam boolean isOAuthLogin) {
        if (!isOAuthLogin && password == null) {
            throw new IllegalArgumentException("Password is required for regular login");
        }

        return authService.login(username, isOAuthLogin);
    }
    @GetMapping("/oauth-success")
    public ResponseEntity<String> oauthSuccess(@RequestParam String token) {
        return ResponseEntity.ok().body(token);
    }
}
