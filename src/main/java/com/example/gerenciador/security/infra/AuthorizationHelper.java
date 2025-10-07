package com.example.gerenciador.security.infra;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import com.example.gerenciador.user.domain.entities.User;
import com.example.gerenciador.user.domain.repository.IUserRepository;

public class AuthorizationHelper {
    
    private final IUserRepository userRepository;
    
    public AuthorizationHelper(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean isAdmin(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }
        
        return authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
    }

    public boolean isUser(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }
        
        return authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_USER"));
    }

    public boolean isAdminOrUser(Authentication authentication) {
        return isAdmin(authentication) || isUser(authentication);
    }

    public ResponseEntity<?> adminAccessDenied() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body("Acesso negado. Apenas administradores podem realizar esta operação.");
    }

    public ResponseEntity<?> userAccessDenied() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body("Acesso negado. Apenas usuários autenticados podem realizar esta operação.");
    }

    public Optional<User> getAuthenticatedUser(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.empty();
        }
        
        try {
            Long userId = Long.parseLong(authentication.getName());
            return userRepository.encontrarPorId(userId);
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }
    

    public boolean isAdminFromDatabase(Authentication authentication) {
        Optional<User> user = getAuthenticatedUser(authentication);
        return user.map(User::isAdmin).orElse(false);
    }

    public boolean isUserFromDatabase(Authentication authentication) {
        Optional<User> user = getAuthenticatedUser(authentication);
        return user.map(User::isUser).orElse(false);
    }
}
