package com.example.gerenciador.produto.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.gerenciador.produto.domain.entities.Produto;
import com.example.gerenciador.produto.infra.filter.ProdutoFilter;

public interface IProdutoRepository {
    
    Optional<Produto> encontrarPorId(Long id);
    
    Produto salvar(Produto produto);
    
    Produto criar(Produto produto);
    
    Produto atualizar(Produto produto);
    
    void deletar(Produto produto);
    
    void deletarPorId(Long id);
    
    Page<Produto> buscarTodos(Pageable pageable);
    
    Page<Produto> buscarTodos(ProdutoFilter filtro, Pageable pageable);
    
    List<Produto> encontrarPorCategoria(String categoria);
    
    List<Produto> encontrarProdutosComEstoqueBaixo(int limite);
}
