package com.valeriobarbershop.backend.controller;

import com.valeriobarbershop.backend.dto.auth.LoginRequest;
import com.valeriobarbershop.backend.dto.auth.RegisterRequest;
import com.valeriobarbershop.backend.model.Utente;
import com.valeriobarbershop.backend.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public Utente register(@RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            return ResponseEntity.ok(authService.login(request));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(401).body(Map.of("error", ex.getMessage()));
        }
    }
}