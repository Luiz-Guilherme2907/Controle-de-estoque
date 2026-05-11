package com.controle.estoque.dto.request;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ProdutoRequest(
        @NotBlank String nome,
        String descricao,
        @NotNull @Min(0) Integer quantidade,
        @NotNull @DecimalMin("0.01") BigDecimal preco
) {}
