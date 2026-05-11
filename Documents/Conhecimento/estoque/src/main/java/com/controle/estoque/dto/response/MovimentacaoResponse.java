package com.controle.estoque.dto.response;

import com.controle.estoque.entity.Movimentacao;

import java.time.Instant;

public record MovimentacaoResponse(
        Long id,
        Long produtoId,
        String nomeProduto,
        Movimentacao.Tipo tipo,
        Integer quantidade,
        String observacao,
        Instant criadoEm
) {
    public static MovimentacaoResponse from(Movimentacao m) {
        return new MovimentacaoResponse(
                m.getId(),
                m.getProduto().getId(),
                m.getProduto().getNome(),
                m.getTipo(),
                m.getQuantidade(),
                m.getObservacao(),
                m.getCriadoEm()
        );
    }
}
