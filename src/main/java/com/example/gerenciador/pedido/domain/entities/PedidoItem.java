package com.example.gerenciador.pedido.domain.entities;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
public class PedidoItem {
    
    private Long id;
    
    private Long pedidoId;
    
    private Long produtoId;
    
    private String nomeProduto;
    
    private BigDecimal precoUnitario;
    
    private Integer quantidade;
    
    private BigDecimal valorTotal;
    
    public PedidoItem() {
        this.valorTotal = BigDecimal.ZERO;
    }
    
    public void calcularValorTotal() {
        this.valorTotal = this.precoUnitario.multiply(BigDecimal.valueOf(this.quantidade));
    }
    
    public static PedidoItem criar(Long pedidoId, Long produtoId, String nomeProduto, BigDecimal precoUnitario, Integer quantidade) {
        PedidoItem item = PedidoItem.builder()
                .pedidoId(pedidoId)
                .produtoId(produtoId)
                .nomeProduto(nomeProduto)
                .precoUnitario(precoUnitario)
                .quantidade(quantidade)
                .build();
        item.calcularValorTotal();
        return item;
    }
}
