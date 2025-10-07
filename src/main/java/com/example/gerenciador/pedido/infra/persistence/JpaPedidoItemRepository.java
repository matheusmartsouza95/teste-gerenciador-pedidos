package com.example.gerenciador.pedido.infra.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.gerenciador.pedido.infra.entities.PedidoItemEntity;

public interface JpaPedidoItemRepository extends JpaRepository<PedidoItemEntity, Long> {
    
    List<PedidoItemEntity> findByPedidoId(Long pedidoId);
    
    void deleteByPedidoId(Long pedidoId);
}
