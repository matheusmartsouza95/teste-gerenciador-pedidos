package com.example.gerenciador.user.infra.mapper;

import com.example.gerenciador.user.domain.entities.User;
import com.example.gerenciador.user.infra.entities.UserEntity;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {
    
    public static User toDomain(UserEntity entity) {
        return User.builder()
                .id(entity.getId())
                .ativo(entity.isAtivo())
                .nome(entity.getNome())
                .email(entity.getEmail())
                .senha(entity.getSenha())
                .role(entity.getRole())
                .dataCriacao(entity.getDataCriacao())
                .dataAtualizacao(entity.getDataAtualizacao())
                .build();
    }
    
    public static UserEntity toEntity(User domain) {
        UserEntity entity = new UserEntity();
        entity.setId(domain.getId());
        entity.setAtivo(domain.isAtivo());
        entity.setNome(domain.getNome());
        entity.setEmail(domain.getEmail());
        entity.setSenha(domain.getSenha());
        entity.setRole(domain.getRole());
        entity.setDataCriacao(domain.getDataCriacao());
        entity.setDataAtualizacao(domain.getDataAtualizacao());
        return entity;
    }
    
    public static List<User> toDomains(List<UserEntity> entities) {
        return entities.stream()
                .map(UserMapper::toDomain)
                .collect(Collectors.toList());
    }
    
    public static List<UserEntity> toEntities(List<User> domains) {
        return domains.stream()
                .map(UserMapper::toEntity)
                .collect(Collectors.toList());
    }
}
