package com.example.gerenciador.user.application.dto;

import com.example.gerenciador.user.domain.entities.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public interface IUserPersistence {
    
    @NotBlank(message = "Nome é obrigatório")
    @Length(max = 255, message = "Nome deve ter no máximo 255 caracteres")
    String getNome();
    
    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email deve ter formato válido")
    @Length(max = 255, message = "Email deve ter no máximo 255 caracteres")
    String getEmail();
    
    @NotBlank(message = "Senha é obrigatória")
    String getSenha();
    
    @NotNull(message = "Role é obrigatória")
    User.UserRole getRole();
}
