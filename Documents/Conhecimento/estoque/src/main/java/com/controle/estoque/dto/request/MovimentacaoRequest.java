package com.controle.estoque.dto.request;

import com.controle.estoque.entity.Movimentacao;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record MovimentacaoRequest(
        @NotNull Long produtoId,
        @NotNull Movimentacao.Tipo tipo,
        @NotNull @Min(1) Integer quantidade,
        String observacao
) {}
