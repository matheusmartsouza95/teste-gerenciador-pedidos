package com.example.gerenciador.pedido.application.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;


public interface IPedidoItemPersistence {
    
    @NotNull(message = "Produto ID é obrigatório")
    Long getProdutoId();
    
    @NotNull(message = "Quantidade é obrigatória")
    @Min(value = 1, message = "Quantidade deve ser maior que zero")
    Integer getQuantidade();
}
