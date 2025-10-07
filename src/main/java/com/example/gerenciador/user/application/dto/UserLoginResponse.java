package com.example.gerenciador.user.application.dto;

import com.example.gerenciador.user.domain.entities.User;

public class UserLoginResponse {
    
    private String token;
    private String email;
    private String nome;
    private User.UserRole role;
    
    public UserLoginResponse() {}
    
    public UserLoginResponse(String token, String email, String nome, User.UserRole role) {
        this.token = token;
        this.email = email;
        this.nome = nome;
        this.role = role;
    }
    
    public String getToken() {
        return token;
    }
    
    public void setToken(String token) {
        this.token = token;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public User.UserRole getRole() {
        return role;
    }
    
    public void setRole(User.UserRole role) {
        this.role = role;
    }
}
