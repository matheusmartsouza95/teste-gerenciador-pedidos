package com.example.gerenciador.pedido.infra.dto;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class CriarPedidoRequestSimplified {
    
    @NotEmpty(message = "Itens do pedido são obrigatórios")
    @Valid
    private List<CriarPedidoItemRequest> itens;
    
    public CriarPedidoRequestSimplified() {}
    
    public CriarPedidoRequestSimplified(List<CriarPedidoItemRequest> itens) {
        this.itens = itens;
    }
    
    public List<CriarPedidoItemRequest> getItens() {
        return itens;
    }
    
    public void setItens(List<CriarPedidoItemRequest> itens) {
        this.itens = itens;
    }
    
    public static class CriarPedidoItemRequest {
        
        @NotNull(message = "Produto ID é obrigatório")
        private Long produtoId;
        
        @NotNull(message = "Quantidade é obrigatória")
        @Min(value = 1, message = "Quantidade deve ser maior que zero")
        private Integer quantidade;
        
        public CriarPedidoItemRequest() {}
        
        public CriarPedidoItemRequest(Long produtoId, Integer quantidade) {
            this.produtoId = produtoId;
            this.quantidade = quantidade;
        }
        
        public Long getProdutoId() {
            return produtoId;
        }
        
        public void setProdutoId(Long produtoId) {
            this.produtoId = produtoId;
        }
        
        public Integer getQuantidade() {
            return quantidade;
        }
        
        public void setQuantidade(Integer quantidade) {
            this.quantidade = quantidade;
        }
    }
}
