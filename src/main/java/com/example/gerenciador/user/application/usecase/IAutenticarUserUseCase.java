package com.example.gerenciador.user.application.usecase;

import com.example.gerenciador.user.application.dto.UserLoginRequest;
import com.example.gerenciador.user.application.dto.UserLoginResponse;

public interface IAutenticarUserUseCase {
    UserLoginResponse autenticar(UserLoginRequest request);
}
