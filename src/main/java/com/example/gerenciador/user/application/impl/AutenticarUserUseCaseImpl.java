package com.example.gerenciador.user.application.impl;

import com.example.gerenciador.user.application.dto.UserLoginRequest;
import com.example.gerenciador.user.application.dto.UserLoginResponse;
import com.example.gerenciador.user.application.usecase.IAutenticarUserUseCase;
import com.example.gerenciador.user.domain.entities.User;
import com.example.gerenciador.user.domain.repository.IUserRepository;
import com.example.gerenciador.security.infra.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AutenticarUserUseCaseImpl implements IAutenticarUserUseCase {
    
    private final IUserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    
    @Override
    public UserLoginResponse autenticar(UserLoginRequest request) {
        
        User user = repository.encontrarPorEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Credenciais inválidas"));
        
        if (!passwordEncoder.matches(request.getSenha(), user.getSenha())) {
            throw new IllegalArgumentException("Credenciais inválidas");
        }
        
        if (!user.isAtivo()) {
            throw new IllegalArgumentException("Usuário inativo");
        }
        
        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());
        
        return new UserLoginResponse(token, user.getEmail(), user.getNome(), user.getRole());
    }
}
