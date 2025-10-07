package com.example.gerenciador.pedido.infra.persistence;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.gerenciador.pedido.infra.entities.PedidoEntity;

public interface JpaPedidoRepository extends JpaRepository<PedidoEntity, Long> {
    
    Page<PedidoEntity> findByUsuarioIdOrderByDataCriacaoDesc(Long usuarioId, Pageable pageable);
    
    List<PedidoEntity> findByStatusOrderByDataCriacaoAsc(com.example.gerenciador.pedido.domain.entities.Pedido.PedidoStatus status);
    
    @Query("SELECT p.usuarioId, COUNT(p) as totalPedidos, SUM(p.valorTotal) as valorTotal " +
           "FROM PedidoEntity p " +
           "WHERE p.status = 'PAGO' " +
           "GROUP BY p.usuarioId " +
           "ORDER BY totalPedidos DESC")
    List<Object[]> findTopUsuariosPorCompras(@Param("limite") int limite);
    
    @Query("SELECT p.usuarioId, COUNT(p) as totalPedidos, SUM(p.valorTotal) as valorTotal, " +
           "AVG(p.valorTotal) as ticketMedio " +
           "FROM PedidoEntity p " +
           "WHERE p.status = 'PAGO' " +
           "GROUP BY p.usuarioId")
    List<Object[]> calculateTicketMedioPorUsuario();
    
    @Query("SELECT COALESCE(SUM(p.valorTotal), 0) " +
           "FROM PedidoEntity p " +
           "WHERE p.status = 'PAGO' " +
           "AND YEAR(p.dataCriacao) = YEAR(CURRENT_DATE) " +
           "AND MONTH(p.dataCriacao) = MONTH(CURRENT_DATE)")
    Double calculateFaturamentoMesAtual();
    
    @Query("SELECT p FROM PedidoEntity p WHERE p.status = 'PENDENTE' ORDER BY p.dataCriacao ASC")
    List<PedidoEntity> findPedidosPendentes();
}
