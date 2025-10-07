package com.example.gerenciador.pedido.infra.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.gerenciador.pedido.domain.repository.IPedidoRepository;

@RestController
@RequestMapping("/api/relatorios")
public class RelatorioController {
    
    @Autowired
    private IPedidoRepository pedidoRepository;
    
    @GetMapping("/top-usuarios")
    public ResponseEntity<List<Map<String, Object>>> topUsuariosPorCompras(@RequestParam(defaultValue = "5") int limite) {
        List<Object[]> resultados = pedidoRepository.buscarTopUsuariosPorCompras(limite);
        
        List<Map<String, Object>> response = resultados.stream()
                .map(resultado -> {
                    Map<String, Object> item = new HashMap<>();
                    item.put("usuarioId", resultado[0]);
                    item.put("totalPedidos", resultado[1]);
                    item.put("valorTotal", resultado[2]);
                    return item;
                })
                .toList();
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/ticket-medio")
    public ResponseEntity<List<Map<String, Object>>> ticketMedioPorUsuario() {
        List<Object[]> resultados = pedidoRepository.calcularTicketMedioPorUsuario();
        
        List<Map<String, Object>> response = resultados.stream()
                .map(resultado -> {
                    Map<String, Object> item = new HashMap<>();
                    item.put("usuarioId", resultado[0]);
                    item.put("totalPedidos", resultado[1]);
                    item.put("valorTotal", resultado[2]);
                    item.put("ticketMedio", resultado[3]);
                    return item;
                })
                .toList();
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/faturamento-mes")
    public ResponseEntity<Map<String, Object>> faturamentoMesAtual() {
        Double faturamento = pedidoRepository.calcularFaturamentoMesAtual();
        
        Map<String, Object> response = new HashMap<>();
        response.put("faturamento", faturamento != null ? faturamento : 0.0);
        
        return ResponseEntity.ok(response);
    }
}
