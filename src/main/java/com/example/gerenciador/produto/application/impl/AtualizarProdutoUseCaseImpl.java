package com.example.gerenciador.produto.application.impl;

import org.springframework.stereotype.Service;

import com.example.gerenciador.produto.application.dto.IProdutoPersistence;
import com.example.gerenciador.produto.application.usecase.IAtualizarProdutoUseCase;
import com.example.gerenciador.produto.domain.entities.Produto;
import com.example.gerenciador.produto.domain.repository.IProdutoRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class AtualizarProdutoUseCaseImpl implements IAtualizarProdutoUseCase {
    
    private final IProdutoRepository repository;
    
    @Override
    public Produto atualizar(Long id, IProdutoPersistence persistence) {
        
        Produto produtoExistente = repository.encontrarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado com ID: " + id));
        
        Produto produtoAtualizado = produtoExistente.toBuilder()
                .nome(persistence.getNome())
                .descricao(persistence.getDescricao())
                .preco(persistence.getPreco())
                .categoria(persistence.getCategoria())
                .quantidadeEstoque(persistence.getQuantidadeEstoque())
                .build();
        
        return repository.atualizar(produtoAtualizado);
    }
}
