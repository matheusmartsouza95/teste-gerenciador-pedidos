package com.example.gerenciador.user.infra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.gerenciador.user.application.dto.UserLoginRequest;
import com.example.gerenciador.user.application.dto.UserLoginResponse;
import com.example.gerenciador.user.application.usecase.IAutenticarUserUseCase;
import com.example.gerenciador.user.application.usecase.ICriarUserUseCase;
import com.example.gerenciador.user.domain.entities.User;
import com.example.gerenciador.user.infra.dto.CriarUserRequest;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    @Autowired
    private ICriarUserUseCase criarUserUseCase;
    
    @Autowired
    private IAutenticarUserUseCase autenticarUserUseCase;
    
    @PostMapping("/register")
    public ResponseEntity<User> register(@Valid @RequestBody CriarUserRequest request) {
        User user = criarUserUseCase.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
    
    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> login(@Valid @RequestBody UserLoginRequest request) {
        UserLoginResponse response = autenticarUserUseCase.autenticar(request);
        return ResponseEntity.ok(response);
    }
}
