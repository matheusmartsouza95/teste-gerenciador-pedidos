package com.example.gerenciador.pedido.application.usecase;

import com.example.gerenciador.pedido.application.dto.IPedidoPersistence;
import com.example.gerenciador.pedido.domain.entities.Pedido;

public interface ICriarPedidoUseCase {
    Pedido criar(IPedidoPersistence persistence);
}
