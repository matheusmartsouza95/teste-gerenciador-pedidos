package com.example.gerenciador.user.domain.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.gerenciador.user.domain.entities.User;

public interface IUserRepository {
    
    Optional<User> encontrarPorId(Long id);
    
    Optional<User> encontrarPorEmail(String email);
    
    User salvar(User user);
    
    User criar(User user);
    
    User atualizar(User user);
    
    void deletar(User user);
    
    void deletarPorId(Long id);
    
    Page<User> buscarTodos(Pageable pageable);
    
    boolean existePorEmail(String email);
}
