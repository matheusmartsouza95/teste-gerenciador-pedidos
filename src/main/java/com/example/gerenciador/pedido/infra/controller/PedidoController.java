package com.example.gerenciador.pedido.infra.controller;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.gerenciador.pedido.application.usecase.IConfirmarPagamentoPedidoUseCase;
import com.example.gerenciador.pedido.application.usecase.ICriarPedidoUseCase;
import com.example.gerenciador.pedido.domain.entities.Pedido;
import com.example.gerenciador.pedido.domain.repository.IPedidoRepository;
import com.example.gerenciador.pedido.infra.dto.CriarPedidoRequest;
import com.example.gerenciador.pedido.infra.dto.CriarPedidoRequestSimplified;
import com.example.gerenciador.security.infra.AuthorizationHelper;
import com.example.gerenciador.user.domain.repository.IUserRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {
    
    @Autowired
    private ICriarPedidoUseCase criarPedidoUseCase;
    
    @Autowired
    private IConfirmarPagamentoPedidoUseCase confirmarPagamentoPedidoUseCase;
    
    @Autowired
    private IPedidoRepository pedidoRepository;
    
    @Autowired
    private IUserRepository userRepository;
    
    private AuthorizationHelper getAuthorizationHelper() {
        return new AuthorizationHelper(userRepository);
    }
    
    @PostMapping
    public ResponseEntity<?> criar(@Valid @RequestBody CriarPedidoRequestSimplified request, Authentication authentication) {
        if (!getAuthorizationHelper().isUserFromDatabase(authentication) &&
            !getAuthorizationHelper().isAdminFromDatabase(authentication)) {
            return getAuthorizationHelper().userAccessDenied();
        }
        
        Long userId = Long.parseLong(authentication.getName());
        
        CriarPedidoRequest fullRequest = new CriarPedidoRequest();
        fullRequest.setUsuarioId(userId);
        
        if (request.getItens() != null && !request.getItens().isEmpty()) {
            List<CriarPedidoRequest.CriarPedidoItemRequest> itensConvertidos = request.getItens().stream()
                    .map(item -> new CriarPedidoRequest.CriarPedidoItemRequest(item.getProdutoId(), item.getQuantidade()))
                    .toList();
            fullRequest.setItens(itensConvertidos);
        } else {
            return ResponseEntity.badRequest().body("Itens do pedido são obrigatórios");
        }
        
        Pedido pedido = criarPedidoUseCase.criar(fullRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(pedido);
    }
    
    @GetMapping
    public ResponseEntity<?> listarPedidosDoUsuario(Authentication authentication, Pageable pageable) {
        if (!getAuthorizationHelper().isUserFromDatabase(authentication) &&
            !getAuthorizationHelper().isAdminFromDatabase(authentication)) {
            return getAuthorizationHelper().userAccessDenied();
        }
        
        String userId = authentication.getName();
        Page<Pedido> pedidos = pedidoRepository.buscarPorUsuario(Long.parseLong(userId), pageable);
        return ResponseEntity.ok(pedidos);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id, Authentication authentication) {
        if (!getAuthorizationHelper().isUserFromDatabase(authentication) &&
            !getAuthorizationHelper().isAdminFromDatabase(authentication)) {
            return getAuthorizationHelper().userAccessDenied();
        }
        
        String userId = authentication.getName();
        
        Optional<Pedido> pedidoOpt = pedidoRepository.encontrarPorId(id);
        if (pedidoOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        Pedido pedido = pedidoOpt.get();
        if (!pedido.getUsuarioId().toString().equals(userId)) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(pedido);
    }
    
    @PostMapping("/{id}/pagar")
    public ResponseEntity<?> confirmarPagamento(@PathVariable Long id, Authentication authentication) {
        if (!getAuthorizationHelper().isUserFromDatabase(authentication) &&
            !getAuthorizationHelper().isAdminFromDatabase(authentication)) {
            return getAuthorizationHelper().userAccessDenied();
        }
        
        String userId = authentication.getName();
        
        Optional<Pedido> pedidoOpt = pedidoRepository.encontrarPorId(id);
        if (pedidoOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        Pedido pedido = pedidoOpt.get();
        if (!pedido.getUsuarioId().toString().equals(userId)) {
            return ResponseEntity.notFound().build();
        }
        
        try {
            Pedido pedidoAtualizado = confirmarPagamentoPedidoUseCase.confirmarPagamento(id);
            return ResponseEntity.ok(pedidoAtualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
