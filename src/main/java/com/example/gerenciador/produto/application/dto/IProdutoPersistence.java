package com.example.gerenciador.produto.application.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

public interface IProdutoPersistence {
    
    @NotBlank(message = "Nome é obrigatório")
    @Length(max = 255, message = "Nome deve ter no máximo 255 caracteres")
    String getNome();
    
    @Length(max = 1000, message = "Descrição deve ter no máximo 1000 caracteres")
    String getDescricao();
    
    @NotNull(message = "Preço é obrigatório")
    @DecimalMin(value = "0.01", message = "Preço deve ser maior que zero")
    BigDecimal getPreco();
    
    @NotBlank(message = "Categoria é obrigatória")
    @Length(max = 100, message = "Categoria deve ter no máximo 100 caracteres")
    String getCategoria();
    
    @NotNull(message = "Quantidade em estoque é obrigatória")
    @Min(value = 0, message = "Quantidade em estoque não pode ser negativa")
    Integer getQuantidadeEstoque();
}
