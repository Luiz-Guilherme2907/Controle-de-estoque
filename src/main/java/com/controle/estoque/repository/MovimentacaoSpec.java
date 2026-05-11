package com.controle.estoque.repository;

import com.controle.estoque.entity.Movimentacao;
import org.springframework.data.jpa.domain.Specification;

import java.time.Instant;

public final class MovimentacaoSpec {

    private MovimentacaoSpec() {}

    public static Specification<Movimentacao> porProduto(Long produtoId) {
        return (root, query, cb) ->
                produtoId == null ? cb.conjunction() : cb.equal(root.get("produto").get("id"), produtoId);
    }

    public static Specification<Movimentacao> porTipo(Movimentacao.Tipo tipo) {
        return (root, query, cb) ->
                tipo == null ? cb.conjunction() : cb.equal(root.get("tipo"), tipo);
    }

    public static Specification<Movimentacao> aPartirDe(Instant de) {
        return (root, query, cb) ->
                de == null ? cb.conjunction() : cb.greaterThanOrEqualTo(root.get("criadoEm"), de);
    }

    public static Specification<Movimentacao> ate(Instant ate) {
        return (root, query, cb) ->
                ate == null ? cb.conjunction() : cb.lessThanOrEqualTo(root.get("criadoEm"), ate);
    }

    public static Specification<Movimentacao> filtrar(Long produtoId, Movimentacao.Tipo tipo, Instant de, Instant ate) {
        return Specification.allOf(porProduto(produtoId), porTipo(tipo), aPartirDe(de), ate(ate));
    }
}
