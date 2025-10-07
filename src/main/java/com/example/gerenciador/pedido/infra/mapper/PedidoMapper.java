package com.example.gerenciador.pedido.infra.mapper;

import com.example.gerenciador.pedido.domain.entities.Pedido;
import com.example.gerenciador.pedido.domain.entities.PedidoItem;
import com.example.gerenciador.pedido.infra.entities.PedidoEntity;
import com.example.gerenciador.pedido.infra.entities.PedidoItemEntity;

import java.util.List;
import java.util.stream.Collectors;

public class PedidoMapper {
    
    public static Pedido toDomain(PedidoEntity entity) {
        List<PedidoItem> itens = entity.getItens().stream()
                .map(PedidoMapper::toDomain)
                .collect(Collectors.toList());
        
        return Pedido.builder()
                .id(entity.getId())
                .usuarioId(entity.getUsuarioId())
                .status(entity.getStatus())
                .itens(itens)
                .valorTotal(entity.getValorTotal())
                .dataCriacao(entity.getDataCriacao())
                .dataAtualizacao(entity.getDataAtualizacao())
                .build();
    }
    
    public static PedidoEntity toEntity(Pedido domain) {
        PedidoEntity entity = new PedidoEntity();
        entity.setId(domain.getId());
        entity.setUsuarioId(domain.getUsuarioId());
        entity.setStatus(domain.getStatus());
        entity.setValorTotal(domain.getValorTotal());
        entity.setDataCriacao(domain.getDataCriacao());
        entity.setDataAtualizacao(domain.getDataAtualizacao());
        
        List<PedidoItemEntity> itensEntities = domain.getItens().stream()
                .map(item -> toEntity(item, entity))
                .collect(Collectors.toList());
        entity.setItens(itensEntities);
        
        return entity;
    }
    
    public static PedidoItem toDomain(PedidoItemEntity entity) {
        return PedidoItem.builder()
                .id(entity.getId())
                .pedidoId(entity.getPedidoId())
                .produtoId(entity.getProdutoId())
                .nomeProduto(entity.getNomeProduto())
                .precoUnitario(entity.getPrecoUnitario())
                .quantidade(entity.getQuantidade())
                .valorTotal(entity.getValorTotal())
                .build();
    }
    
    public static PedidoItemEntity toEntity(PedidoItem domain, PedidoEntity pedidoEntity) {
        PedidoItemEntity entity = new PedidoItemEntity();
        entity.setId(domain.getId());
        entity.setPedidoId(domain.getPedidoId());
        entity.setProdutoId(domain.getProdutoId());
        entity.setNomeProduto(domain.getNomeProduto());
        entity.setPrecoUnitario(domain.getPrecoUnitario());
        entity.setQuantidade(domain.getQuantidade());
        entity.setValorTotal(domain.getValorTotal());
        entity.setPedido(pedidoEntity);
        return entity;
    }
    
    public static List<Pedido> toDomains(List<PedidoEntity> entities) {
        return entities.stream()
                .map(PedidoMapper::toDomain)
                .collect(Collectors.toList());
    }
}
