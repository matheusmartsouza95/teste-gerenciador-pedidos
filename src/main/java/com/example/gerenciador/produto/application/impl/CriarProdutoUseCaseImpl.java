package com.example.gerenciador.produto.application.impl;


import org.springframework.stereotype.Service;

import com.example.gerenciador.produto.application.dto.IProdutoPersistence;
import com.example.gerenciador.produto.application.usecase.ICriarProdutoUseCase;
import com.example.gerenciador.produto.domain.entities.Produto;
import com.example.gerenciador.produto.domain.repository.IProdutoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CriarProdutoUseCaseImpl implements ICriarProdutoUseCase {
    
    private final IProdutoRepository repository;
    
    @Override
    public Produto criar(IProdutoPersistence persistence) {
        
        Produto produto = Produto.builder()
                .nome(persistence.getNome())
                .descricao(persistence.getDescricao())
                .preco(persistence.getPreco())
                .categoria(persistence.getCategoria())
                .quantidadeEstoque(persistence.getQuantidadeEstoque())
                .ativo(true)
                .build();
        
        return repository.criar(produto);
    }
}
