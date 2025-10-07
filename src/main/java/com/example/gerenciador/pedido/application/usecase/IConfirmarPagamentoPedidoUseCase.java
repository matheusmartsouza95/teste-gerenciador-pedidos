package com.example.gerenciador.pedido.application.usecase;

import com.example.gerenciador.pedido.domain.entities.Pedido;


public interface IConfirmarPagamentoPedidoUseCase {
    Pedido confirmarPagamento(Long pedidoId);
}
