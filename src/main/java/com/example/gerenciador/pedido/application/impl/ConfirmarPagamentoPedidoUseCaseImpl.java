package com.example.gerenciador.pedido.application.impl;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.gerenciador.pedido.application.usecase.IConfirmarPagamentoPedidoUseCase;
import com.example.gerenciador.pedido.domain.entities.Pedido;
import com.example.gerenciador.pedido.domain.entities.PedidoItem;
import com.example.gerenciador.pedido.domain.repository.IPedidoRepository;
import com.example.gerenciador.produto.domain.entities.Produto;
import com.example.gerenciador.produto.domain.repository.IProdutoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ConfirmarPagamentoPedidoUseCaseImpl implements IConfirmarPagamentoPedidoUseCase {
    
    private final IPedidoRepository pedidoRepository;
    private final IProdutoRepository produtoRepository;
    
    @Override
    public Pedido confirmarPagamento(Long pedidoId) {
        
        Pedido pedido = pedidoRepository.encontrarPorId(pedidoId)
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado: " + pedidoId));
        
        if (!pedido.isPendente()) {
            throw new IllegalArgumentException("Pedido não está pendente para pagamento");
        }
        
        for (PedidoItem item : pedido.getItens()) {
            Produto produto = produtoRepository.encontrarPorId(item.getProdutoId())
                    .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado: " + item.getProdutoId()));
            
            if (!produto.temEstoque(item.getQuantidade())) {
                pedido.cancelar();
                pedidoRepository.atualizar(pedido);
                throw new IllegalArgumentException("Estoque insuficiente para o produto: " + produto.getNome() + 
                        ". Pedido cancelado automaticamente.");
            }
        }
        
        pedido.confirmarPagamento();
        
        for (PedidoItem item : pedido.getItens()) {
            Produto produto = produtoRepository.encontrarPorId(item.getProdutoId()).get();
            produto.decrementarEstoque(item.getQuantidade());
            produtoRepository.atualizar(produto);
        }
        
        return pedidoRepository.atualizar(pedido);
    }
}
