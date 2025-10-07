package com.example.gerenciador.user.application.usecase;

import com.example.gerenciador.user.application.dto.IUserPersistence;
import com.example.gerenciador.user.domain.entities.User;

public interface ICriarUserUseCase {
    User criar(IUserPersistence persistence);
}
