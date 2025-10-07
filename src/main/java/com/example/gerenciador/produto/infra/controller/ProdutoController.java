package com.example.gerenciador.produto.infra.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.gerenciador.produto.application.usecase.IAtualizarProdutoUseCase;
import com.example.gerenciador.produto.application.usecase.ICriarProdutoUseCase;
import com.example.gerenciador.produto.application.usecase.IDeletarProdutoUseCase;
import com.example.gerenciador.produto.domain.entities.Produto;
import com.example.gerenciador.produto.domain.repository.IProdutoRepository;
import com.example.gerenciador.produto.infra.dto.CriarProdutoRequest;
import com.example.gerenciador.produto.infra.filter.ProdutoFilter;
import com.example.gerenciador.security.infra.AuthorizationHelper;
import com.example.gerenciador.user.domain.repository.IUserRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {
    
    @Autowired
    private ICriarProdutoUseCase criarProdutoUseCase;
    
    @Autowired
    private IAtualizarProdutoUseCase atualizarProdutoUseCase;
    
    @Autowired
    private IDeletarProdutoUseCase deletarProdutoUseCase;
    
    @Autowired
    private IProdutoRepository produtoRepository;
    
    @Autowired
    private IUserRepository userRepository;
    
    private AuthorizationHelper getAuthorizationHelper() {
        return new AuthorizationHelper(userRepository);
    }
    
    @PostMapping
    public ResponseEntity<?> criar(@Valid @RequestBody CriarProdutoRequest request, Authentication authentication) {
        if (!getAuthorizationHelper().isAdminFromDatabase(authentication)) {
            return getAuthorizationHelper().adminAccessDenied();
        }
        
        Produto produto = criarProdutoUseCase.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(produto);
    }
    
    @GetMapping
    public ResponseEntity<Page<Produto>> listar(ProdutoFilter filtro, Pageable pageable, Authentication authentication) {
        if (!getAuthorizationHelper().isAdminFromDatabase(authentication) &&
            !getAuthorizationHelper().isUserFromDatabase(authentication)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        
        Page<Produto> produtos = produtoRepository.buscarTodos(filtro, pageable);
        return ResponseEntity.ok(produtos);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id, Authentication authentication) {
        if (!getAuthorizationHelper().isAdminFromDatabase(authentication) &&
            !getAuthorizationHelper().isUserFromDatabase(authentication)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        
        return produtoRepository.encontrarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @Valid @RequestBody CriarProdutoRequest request, Authentication authentication) {
        if (!getAuthorizationHelper().isAdminFromDatabase(authentication)) {
            return getAuthorizationHelper().adminAccessDenied();
        }
        
        try {
            Produto produto = atualizarProdutoUseCase.atualizar(id, request);
            return ResponseEntity.ok(produto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id, Authentication authentication) {
        if (!getAuthorizationHelper().isAdminFromDatabase(authentication)) {
            return getAuthorizationHelper().adminAccessDenied();
        }
        
        try {
            deletarProdutoUseCase.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<?> buscarPorCategoria(@PathVariable String categoria, Authentication authentication) {
        if (!getAuthorizationHelper().isAdminFromDatabase(authentication) &&
            !getAuthorizationHelper().isUserFromDatabase(authentication)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        
        List<Produto> produtos = produtoRepository.encontrarPorCategoria(categoria);
        return ResponseEntity.ok(produtos);
    }
}
