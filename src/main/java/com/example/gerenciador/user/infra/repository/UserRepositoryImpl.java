package com.example.gerenciador.user.infra.repository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.example.gerenciador.user.domain.entities.User;
import com.example.gerenciador.user.domain.repository.IUserRepository;
import com.example.gerenciador.user.infra.entities.UserEntity;
import com.example.gerenciador.user.infra.mapper.UserMapper;
import com.example.gerenciador.user.infra.persistence.JpaUserRepository;

@Component
public class UserRepositoryImpl implements IUserRepository {
    
    @Autowired
    private JpaUserRepository jpaRepository;
    
    @Override
    public Optional<User> encontrarPorId(Long id) {
        return jpaRepository.findById(id)
                .map(UserMapper::toDomain);
    }
    
    @Override
    public Optional<User> encontrarPorEmail(String email) {
        return jpaRepository.findByEmailIgnoreCase(email)
                .map(UserMapper::toDomain);
    }
    
    @Override
    public User salvar(User user) {
        UserEntity entity = UserMapper.toEntity(user);
        return UserMapper.toDomain(jpaRepository.save(entity));
    }
    
    @Override
    public User criar(User user) {
        return salvar(user);
    }
    
    @Override
    public User atualizar(User user) {
        return salvar(user);
    }
    
    @Override
    public void deletar(User user) {
        UserEntity entity = UserMapper.toEntity(user);
        jpaRepository.delete(entity);
    }
    
    @Override
    public void deletarPorId(Long id) {
        jpaRepository.deleteById(id);
    }
    
    @Override
    public Page<User> buscarTodos(Pageable pageable) {
        return jpaRepository.findAllByOrderByDataCriacaoDesc(pageable)
                .map(UserMapper::toDomain);
    }
    
    @Override
    public boolean existePorEmail(String email) {
        return jpaRepository.existsByEmailIgnoreCase(email);
    }
}
