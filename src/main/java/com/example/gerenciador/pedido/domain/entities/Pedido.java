package com.example.gerenciador.pedido.domain.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
public class Pedido {
    
    private Long id;
    
    private Long usuarioId;
    
    private PedidoStatus status;
    
    private List<PedidoItem> itens;
    
    private BigDecimal valorTotal;
    
    private LocalDateTime dataCriacao;
    
    private LocalDateTime dataAtualizacao;
    
    public Pedido() {
        this.itens = new ArrayList<>();
        this.status = PedidoStatus.PENDENTE;
        this.valorTotal = BigDecimal.ZERO;
    }
    
    public void adicionarItem(PedidoItem item) {
        if (this.itens == null) {
            this.itens = new ArrayList<>();
        }
        this.itens.add(item);
        this.calcularValorTotal();
    }
    
    public void removerItem(PedidoItem item) {
        if (this.itens != null) {
            this.itens.remove(item);
            this.calcularValorTotal();
        }
    }
    
    public void calcularValorTotal() {
        if (this.itens == null || this.itens.isEmpty()) {
            this.valorTotal = BigDecimal.ZERO;
        } else {
            this.valorTotal = this.itens.stream()
                    .map(PedidoItem::getValorTotal)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        }
    }
    
    public void confirmarPagamento() {
        if (this.status != PedidoStatus.PENDENTE) {
            throw new IllegalStateException("Pedido não está pendente para confirmação de pagamento");
        }
        this.status = PedidoStatus.PAGO;
    }
    
    public void cancelar() {
        if (this.status == PedidoStatus.CANCELADO) {
            throw new IllegalStateException("Pedido já está cancelado");
        }
        this.status = PedidoStatus.CANCELADO;
    }
    
    public boolean isPendente() {
        return PedidoStatus.PENDENTE.equals(this.status);
    }
    
    public boolean isPago() {
        return PedidoStatus.PAGO.equals(this.status);
    }
    
    public boolean isCancelado() {
        return PedidoStatus.CANCELADO.equals(this.status);
    }
    
    public enum PedidoStatus {
        PENDENTE, PAGO, CANCELADO
    }
}
