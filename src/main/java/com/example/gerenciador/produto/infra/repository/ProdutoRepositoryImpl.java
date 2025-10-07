package com.example.gerenciador.produto.infra.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.example.gerenciador.produto.domain.entities.Produto;
import com.example.gerenciador.produto.domain.repository.IProdutoRepository;
import com.example.gerenciador.produto.infra.entities.ProdutoEntity;
import com.example.gerenciador.produto.infra.filter.ProdutoFilter;
import com.example.gerenciador.produto.infra.mapper.ProdutoMapper;
import com.example.gerenciador.produto.infra.persistence.JpaProdutoRepository;

@Component
public class ProdutoRepositoryImpl implements IProdutoRepository {
    
    @Autowired
    private JpaProdutoRepository jpaRepository;
    
    @Override
    public Optional<Produto> encontrarPorId(Long id) {
        return jpaRepository.findById(id)
                .map(ProdutoMapper::toDomain);
    }
    
    @Override
    public Produto salvar(Produto produto) {
        ProdutoEntity entity = ProdutoMapper.toEntity(produto);
        return ProdutoMapper.toDomain(jpaRepository.save(entity));
    }
    
    @Override
    public Produto criar(Produto produto) {
        return salvar(produto);
    }
    
    @Override
    public Produto atualizar(Produto produto) {
        return salvar(produto);
    }
    
    @Override
    public void deletar(Produto produto) {
        ProdutoEntity entity = ProdutoMapper.toEntity(produto);
        jpaRepository.delete(entity);
    }
    
    @Override
    public void deletarPorId(Long id) {
        jpaRepository.deleteById(id);
    }
    
    @Override
    public Page<Produto> buscarTodos(Pageable pageable) {
        return jpaRepository.findAllByOrderByDataCriacaoDesc(pageable)
                .map(ProdutoMapper::toDomain);
    }
    
    @Override
    public Page<Produto> buscarTodos(ProdutoFilter filtro, Pageable pageable) {
        return jpaRepository.findAll(filtro.where(), pageable)
                .map(ProdutoMapper::toDomain);
    }
    
    @Override
    public List<Produto> encontrarPorCategoria(String categoria) {
        return ProdutoMapper.toDomains(jpaRepository.findByCategoriaIgnoreCase(categoria));
    }
    
    @Override
    public List<Produto> encontrarProdutosComEstoqueBaixo(int limite) {
        return ProdutoMapper.toDomains(jpaRepository.findProdutosComEstoqueBaixo(limite));
    }
}
