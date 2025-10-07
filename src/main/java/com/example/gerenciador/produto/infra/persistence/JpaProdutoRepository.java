package com.example.gerenciador.produto.infra.persistence;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.gerenciador.produto.infra.entities.ProdutoEntity;

public interface JpaProdutoRepository extends JpaRepository<ProdutoEntity, Long> {
    
    Page<ProdutoEntity> findAllByOrderByDataCriacaoDesc(Pageable pageable);
    
    Page<ProdutoEntity> findAll(Specification<ProdutoEntity> spec, Pageable pageable);
    
    List<ProdutoEntity> findByCategoriaIgnoreCase(String categoria);
    
    @Query("SELECT p FROM ProdutoEntity p WHERE p.ativo = true AND p.quantidadeEstoque <= :limite")
    List<ProdutoEntity> findProdutosComEstoqueBaixo(@Param("limite") int limite);
    
    @Query("SELECT p FROM ProdutoEntity p WHERE p.ativo = true AND p.quantidadeEstoque > 0")
    List<ProdutoEntity> findProdutosComEstoque();
}
