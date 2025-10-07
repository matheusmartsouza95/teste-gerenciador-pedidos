package com.example.gerenciador.user.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
public class User {
    
    private Long id;
    
    @Builder.Default
    private boolean ativo = true;
    
    private String nome;
    
    private String email;
    
    private String senha;
    
    private UserRole role;
    
    private LocalDateTime dataCriacao;
    
    private LocalDateTime dataAtualizacao;
    
    public boolean isAdmin() {
        return UserRole.ADMIN.equals(this.role);
    }
    
    public boolean isUser() {
        return UserRole.USER.equals(this.role);
    }
    
    public enum UserRole {
        ADMIN, USER
    }
}
