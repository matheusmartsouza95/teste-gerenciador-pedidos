package com.example.gerenciador.produto.application.impl;

import org.springframework.stereotype.Service;

import com.example.gerenciador.produto.application.usecase.IDeletarProdutoUseCase;
import com.example.gerenciador.produto.domain.entities.Produto;
import com.example.gerenciador.produto.domain.repository.IProdutoRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class DeletarProdutoUseCaseImpl implements IDeletarProdutoUseCase {
    
    private final IProdutoRepository repository;
    
    @Override
    public void deletar(Long id) {
        
        Produto produto = repository.encontrarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado com ID: " + id));
        
        repository.deletar(produto);
    }
}
