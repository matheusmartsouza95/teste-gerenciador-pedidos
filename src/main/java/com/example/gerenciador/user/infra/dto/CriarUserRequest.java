package com.example.gerenciador.user.infra.dto;

import org.hibernate.validator.constraints.Length;

import com.example.gerenciador.user.application.dto.IUserPersistence;
import com.example.gerenciador.user.domain.entities.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CriarUserRequest implements IUserPersistence {
    
    @NotBlank(message = "Nome é obrigatório")
    @Length(max = 255, message = "Nome deve ter no máximo 255 caracteres")
    private String nome;
    
    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email deve ter formato válido")
    @Length(max = 255, message = "Email deve ter no máximo 255 caracteres")
    private String email;
    
    @NotBlank(message = "Senha é obrigatória")
    private String senha;
    
    @NotNull(message = "Role é obrigatória")
    private User.UserRole role;
    
    public CriarUserRequest() {}
    
    public CriarUserRequest(String nome, String email, String senha, User.UserRole role) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.role = role;
    }
    
    @Override
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    @Override
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    @Override
    public String getSenha() {
        return senha;
    }
    
    public void setSenha(String senha) {
        this.senha = senha;
    }
    
    @Override
    public User.UserRole getRole() {
        return role;
    }
    
    public void setRole(User.UserRole role) {
        this.role = role;
    }
}
