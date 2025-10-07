package com.example.gerenciador.pedido.infra.dto;

import java.util.List;

import com.example.gerenciador.pedido.application.dto.IPedidoItemPersistence;
import com.example.gerenciador.pedido.application.dto.IPedidoPersistence;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class CriarPedidoRequest implements IPedidoPersistence {
    
    @NotNull(message = "Usuário ID é obrigatório")
    private Long usuarioId;
    
    @NotEmpty(message = "Itens do pedido são obrigatórios")
    @Valid
    private List<CriarPedidoItemRequest> itens;
    
    public CriarPedidoRequest() {}
    
    public CriarPedidoRequest(Long usuarioId, List<CriarPedidoItemRequest> itens) {
        this.usuarioId = usuarioId;
        this.itens = itens;
    }
    
    @Override
    public Long getUsuarioId() {
        return usuarioId;
    }
    
    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }
    
    @Override
    public List<IPedidoItemPersistence> getItens() {
        if (itens == null) {
            return List.of();
        }
        return itens.stream()
                .map(item -> (IPedidoItemPersistence) item)
                .toList();
    }
    
    public void setItens(List<CriarPedidoItemRequest> itens) {
        this.itens = itens;
    }
    
    public static class CriarPedidoItemRequest implements IPedidoItemPersistence {
        
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
        
        @Override
        public Long getProdutoId() {
            return produtoId;
        }
        
        public void setProdutoId(Long produtoId) {
            this.produtoId = produtoId;
        }
        
        @Override
        public Integer getQuantidade() {
            return quantidade;
        }
        
        public void setQuantidade(Integer quantidade) {
            this.quantidade = quantidade;
        }
    }
}
