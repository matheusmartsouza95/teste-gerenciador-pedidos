package com.example.gerenciador.produto.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
public class Produto {
    
    private Long id;
    
    @Builder.Default
    private boolean ativo = true;
    
    private String nome;
    
    private String descricao;
    
    private BigDecimal preco;
    
    private String categoria;
    
    private Integer quantidadeEstoque;
    
    private LocalDateTime dataCriacao;
    
    private LocalDateTime dataAtualizacao;
    
    public boolean temEstoque(int quantidade) {
        return this.quantidadeEstoque >= quantidade;
    }
    
    public void decrementarEstoque(int quantidade) {
        if (!temEstoque(quantidade)) {
            throw new IllegalArgumentException("Estoque insuficiente. Disponível: " + this.quantidadeEstoque + ", Solicitado: " + quantidade);
        }
        this.quantidadeEstoque -= quantidade;
    }
    
    public void incrementarEstoque(int quantidade) {
        this.quantidadeEstoque += quantidade;
    }
    
    public BigDecimal calcularValorTotal(int quantidade) {
        return this.preco.multiply(BigDecimal.valueOf(quantidade));
    }
}
