package com.example.gerenciador.user.infra.entities;

import com.example.gerenciador.user.domain.entities.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "ativo", nullable = false)
    private boolean ativo = true;
    
    @Column(name = "nome", nullable = false, length = 255)
    @Length(max = 255, message = "Nome deve ter no máximo 255 caracteres")
    private String nome;
    
    @Column(name = "email", nullable = false, unique = true, length = 255)
    @Length(max = 255, message = "Email deve ter no máximo 255 caracteres")
    private String email;
    
    @Column(name = "senha", nullable = false)
    private String senha;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private User.UserRole role;
    
    @Column(name = "data_criacao", nullable = false)
    private LocalDateTime dataCriacao;
    
    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;
    
    @PrePersist
    protected void onCreate() {
        dataCriacao = LocalDateTime.now();
        dataAtualizacao = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        dataAtualizacao = LocalDateTime.now();
    }
}
