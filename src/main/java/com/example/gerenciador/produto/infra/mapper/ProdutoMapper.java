package com.example.gerenciador.produto.infra.mapper;

import com.example.gerenciador.produto.domain.entities.Produto;
import com.example.gerenciador.produto.infra.entities.ProdutoEntity;

import java.util.List;
import java.util.stream.Collectors;

public class ProdutoMapper {
    
    public static Produto toDomain(ProdutoEntity entity) {
        return Produto.builder()
                .id(entity.getId())
                .ativo(entity.isAtivo())
                .nome(entity.getNome())
                .descricao(entity.getDescricao())
                .preco(entity.getPreco())
                .categoria(entity.getCategoria())
                .quantidadeEstoque(entity.getQuantidadeEstoque())
                .dataCriacao(entity.getDataCriacao())
                .dataAtualizacao(entity.getDataAtualizacao())
                .build();
    }
    
    public static ProdutoEntity toEntity(Produto domain) {
        ProdutoEntity entity = new ProdutoEntity();
        entity.setId(domain.getId());
        entity.setAtivo(domain.isAtivo());
        entity.setNome(domain.getNome());
        entity.setDescricao(domain.getDescricao());
        entity.setPreco(domain.getPreco());
        entity.setCategoria(domain.getCategoria());
        entity.setQuantidadeEstoque(domain.getQuantidadeEstoque());
        entity.setDataCriacao(domain.getDataCriacao());
        entity.setDataAtualizacao(domain.getDataAtualizacao());
        return entity;
    }
    
    public static List<Produto> toDomains(List<ProdutoEntity> entities) {
        return entities.stream()
                .map(ProdutoMapper::toDomain)
                .collect(Collectors.toList());
    }
    
    public static List<ProdutoEntity> toEntities(List<Produto> domains) {
        return domains.stream()
                .map(ProdutoMapper::toEntity)
                .collect(Collectors.toList());
    }
}
