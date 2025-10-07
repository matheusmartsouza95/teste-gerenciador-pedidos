package com.example.gerenciador.pedido.application.dto;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public interface IPedidoPersistence {
    
    @NotNull(message = "Usuário ID é obrigatório")
    Long getUsuarioId();
    
    @NotEmpty(message = "Itens do pedido são obrigatórios")
    @Valid
    List<IPedidoItemPersistence> getItens();
}
