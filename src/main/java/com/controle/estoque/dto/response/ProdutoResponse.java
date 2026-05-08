package com.controle.estoque.dto.response;

import com.controle.estoque.entity.Produto;

import java.math.BigDecimal;

public record ProdutoResponse(
        Long id,
        String nome,
        String descricao,
        Integer quantidade,
        BigDecimal preco,
        Boolean ativo
) {
    public static ProdutoResponse from(Produto p) {
        return new ProdutoResponse(p.getId(), p.getNome(), p.getDescricao(),
                p.getQuantidade(), p.getPreco(), p.getAtivo());
    }
}
