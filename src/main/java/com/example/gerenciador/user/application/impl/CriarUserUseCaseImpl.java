package com.example.gerenciador.user.application.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.gerenciador.user.application.dto.IUserPersistence;
import com.example.gerenciador.user.application.usecase.ICriarUserUseCase;
import com.example.gerenciador.user.domain.entities.User;
import com.example.gerenciador.user.domain.repository.IUserRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class CriarUserUseCaseImpl implements ICriarUserUseCase {
    
    private final IUserRepository repository;
    private final PasswordEncoder passwordEncoder;
    
    @Override
    public User criar(IUserPersistence persistence) {
        
        if (repository.existePorEmail(persistence.getEmail())) {
            throw new IllegalArgumentException("Email já cadastrado: " + persistence.getEmail());
        }
        
        User user = User.builder()
                .nome(persistence.getNome())
                .email(persistence.getEmail())
                .senha(passwordEncoder.encode(persistence.getSenha()))
                .role(persistence.getRole())
                .ativo(true)
                .build();
        
        return repository.criar(user);
    }
}
