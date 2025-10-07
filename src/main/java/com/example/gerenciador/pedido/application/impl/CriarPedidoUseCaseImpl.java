package com.example.gerenciador.pedido.application.impl;


import org.springframework.stereotype.Service;

import com.example.gerenciador.pedido.application.dto.IPedidoItemPersistence;
import com.example.gerenciador.pedido.application.dto.IPedidoPersistence;
import com.example.gerenciador.pedido.application.usecase.ICriarPedidoUseCase;
import com.example.gerenciador.pedido.domain.entities.Pedido;
import com.example.gerenciador.pedido.domain.entities.PedidoItem;
import com.example.gerenciador.pedido.domain.repository.IPedidoRepository;
import com.example.gerenciador.produto.domain.entities.Produto;
import com.example.gerenciador.produto.domain.repository.IProdutoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CriarPedidoUseCaseImpl implements ICriarPedidoUseCase {
    
    private final IPedidoRepository pedidoRepository;
    private final IProdutoRepository produtoRepository;
    
    @Override
    public Pedido criar(IPedidoPersistence persistence) {
        
        Pedido pedido = Pedido.builder()
                .usuarioId(persistence.getUsuarioId())
                .itens(new java.util.ArrayList<>())
                .status(Pedido.PedidoStatus.PENDENTE)
                .valorTotal(java.math.BigDecimal.ZERO)
                .build();
        
        pedido = pedidoRepository.criar(pedido);
        
        for (IPedidoItemPersistence itemPersistence : persistence.getItens()) {
            Produto produto = produtoRepository.encontrarPorId(itemPersistence.getProdutoId())
                    .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado: " + itemPersistence.getProdutoId()));
            
            if (!produto.isAtivo()) {
                throw new IllegalArgumentException("Produto inativo: " + produto.getNome());
            }
            
            if (!produto.temEstoque(itemPersistence.getQuantidade())) {
                throw new IllegalArgumentException("Estoque insuficiente para o produto: " + produto.getNome() + 
                        ". Disponível: " + produto.getQuantidadeEstoque() + 
                        ", Solicitado: " + itemPersistence.getQuantidade());
            }
            
            PedidoItem item = PedidoItem.criar(
                    pedido.getId(),
                    produto.getId(),
                    produto.getNome(),
                    produto.getPreco(),
                    itemPersistence.getQuantidade()
            );
            
            pedido.adicionarItem(item);
        }
        
        return pedidoRepository.atualizar(pedido);
    }
}
