package com.example.gerenciador.produto.infra.filter;

import com.example.gerenciador.produto.infra.entities.ProdutoEntity;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class ProdutoFilter {
    
    private String nome;
    private String categoria;
    private BigDecimal precoMin;
    private BigDecimal precoMax;
    private Boolean ativo;
    
    public ProdutoFilter() {}
    
    public ProdutoFilter(String nome, String categoria, BigDecimal precoMin, BigDecimal precoMax, Boolean ativo) {
        this.nome = nome;
        this.categoria = categoria;
        this.precoMin = precoMin;
        this.precoMax = precoMax;
        this.ativo = ativo;
    }
    
    public Specification<ProdutoEntity> where() {
        return nomeContains()
                .and(categoriaEquals())
                .and(precoMinGreaterThanOrEqualTo())
                .and(precoMaxLessThanOrEqualTo())
                .and(ativoEquals());
    }
    
    private Specification<ProdutoEntity> nomeContains() {
        return (root, query, cb) -> {
            if (nome == null || nome.trim().isEmpty()) {
                return cb.conjunction();
            }
            return cb.like(cb.lower(root.get("nome")), "%" + nome.toLowerCase() + "%");
        };
    }
    
    private Specification<ProdutoEntity> categoriaEquals() {
        return (root, query, cb) -> {
            if (categoria == null || categoria.trim().isEmpty()) {
                return cb.conjunction();
            }
            return cb.equal(cb.lower(root.get("categoria")), categoria.toLowerCase());
        };
    }
    
    private Specification<ProdutoEntity> precoMinGreaterThanOrEqualTo() {
        return (root, query, cb) -> {
            if (precoMin == null) {
                return cb.conjunction();
            }
            return cb.greaterThanOrEqualTo(root.get("preco"), precoMin);
        };
    }
    
    private Specification<ProdutoEntity> precoMaxLessThanOrEqualTo() {
        return (root, query, cb) -> {
            if (precoMax == null) {
                return cb.conjunction();
            }
            return cb.lessThanOrEqualTo(root.get("preco"), precoMax);
        };
    }
    
    private Specification<ProdutoEntity> ativoEquals() {
        return (root, query, cb) -> {
            if (ativo == null) {
                return cb.conjunction();
            }
            return cb.equal(root.get("ativo"), ativo);
        };
    }
    
    // Getters e Setters
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    
    public BigDecimal getPrecoMin() { return precoMin; }
    public void setPrecoMin(BigDecimal precoMin) { this.precoMin = precoMin; }
    
    public BigDecimal getPrecoMax() { return precoMax; }
    public void setPrecoMax(BigDecimal precoMax) { this.precoMax = precoMax; }
    
    public Boolean getAtivo() { return ativo; }
    public void setAtivo(Boolean ativo) { this.ativo = ativo; }
}
