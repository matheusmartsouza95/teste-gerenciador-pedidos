package com.example.gerenciador.pedido.infra.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.example.gerenciador.pedido.domain.entities.Pedido;
import com.example.gerenciador.pedido.domain.repository.IPedidoRepository;
import com.example.gerenciador.pedido.infra.entities.PedidoEntity;
import com.example.gerenciador.pedido.infra.mapper.PedidoMapper;
import com.example.gerenciador.pedido.infra.persistence.JpaPedidoRepository;

@Component
public class PedidoRepositoryImpl implements IPedidoRepository {
    
    @Autowired
    private JpaPedidoRepository jpaRepository;
    
    @Override
    public Optional<Pedido> encontrarPorId(Long id) {
        return jpaRepository.findById(id)
                .map(PedidoMapper::toDomain);
    }
    
    @Override
    public Pedido salvar(Pedido pedido) {
        PedidoEntity entity = PedidoMapper.toEntity(pedido);
        return PedidoMapper.toDomain(jpaRepository.save(entity));
    }
    
    @Override
    public Pedido criar(Pedido pedido) {
        return salvar(pedido);
    }
    
    @Override
    public Pedido atualizar(Pedido pedido) {
        return salvar(pedido);
    }
    
    @Override
    public void deletar(Pedido pedido) {
        PedidoEntity entity = PedidoMapper.toEntity(pedido);
        jpaRepository.delete(entity);
    }
    
    @Override
    public void deletarPorId(Long id) {
        jpaRepository.deleteById(id);
    }
    
    @Override
    public Page<Pedido> buscarTodos(Pageable pageable) {
        return jpaRepository.findAll(pageable)
                .map(PedidoMapper::toDomain);
    }
    
    @Override
    public Page<Pedido> buscarPorUsuario(Long usuarioId, Pageable pageable) {
        return jpaRepository.findByUsuarioIdOrderByDataCriacaoDesc(usuarioId, pageable)
                .map(PedidoMapper::toDomain);
    }
    
    @Override
    public List<Pedido> buscarPedidosPendentes() {
        return PedidoMapper.toDomains(jpaRepository.findPedidosPendentes());
    }
    
    @Override
    public List<Object[]> buscarTopUsuariosPorCompras(int limite) {
        return jpaRepository.findTopUsuariosPorCompras(limite);
    }
    
    @Override
    public List<Object[]> calcularTicketMedioPorUsuario() {
        return jpaRepository.calculateTicketMedioPorUsuario();
    }
    
    @Override
    public Double calcularFaturamentoMesAtual() {
        return jpaRepository.calculateFaturamentoMesAtual();
    }
}
