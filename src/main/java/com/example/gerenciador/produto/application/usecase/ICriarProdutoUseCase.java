package com.example.gerenciador.produto.application.usecase;

import com.example.gerenciador.produto.application.dto.IProdutoPersistence;
import com.example.gerenciador.produto.domain.entities.Produto;

public interface ICriarProdutoUseCase {
    Produto criar(IProdutoPersistence persistence);
}
