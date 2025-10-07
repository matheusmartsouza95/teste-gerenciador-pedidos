package com.example.gerenciador.user.infra.persistence;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.gerenciador.user.infra.entities.UserEntity;

public interface JpaUserRepository extends JpaRepository<UserEntity, Long> {
    
    Optional<UserEntity> findByEmailIgnoreCase(String email);
    
    boolean existsByEmailIgnoreCase(String email);
    
    Page<UserEntity> findAllByOrderByDataCriacaoDesc(Pageable pageable);
    
    @Query("SELECT u FROM UserEntity u WHERE u.ativo = true")
    Page<UserEntity> findActiveUsers(Pageable pageable);
}
