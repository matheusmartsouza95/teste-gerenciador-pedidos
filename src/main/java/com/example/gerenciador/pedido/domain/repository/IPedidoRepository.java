package com.example.gerenciador.pedido.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.gerenciador.pedido.domain.entities.Pedido;

public interface IPedidoRepository {
    
    Optional<Pedido> encontrarPorId(Long id);
    
    Pedido salvar(Pedido pedido);
    
    Pedido criar(Pedido pedido);
    
    Pedido atualizar(Pedido pedido);
    
    void deletar(Pedido pedido);
    
    void deletarPorId(Long id);
    
    Page<Pedido> buscarTodos(Pageable pageable);
    
    Page<Pedido> buscarPorUsuario(Long usuarioId, Pageable pageable);
    
    List<Pedido> buscarPedidosPendentes();
    
    List<Object[]> buscarTopUsuariosPorCompras(int limite);
    
    List<Object[]> calcularTicketMedioPorUsuario();
    
    Double calcularFaturamentoMesAtual();
}
