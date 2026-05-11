package com.controle.estoque.repository;

import com.controle.estoque.entity.Movimentacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Long>, JpaSpecificationExecutor<Movimentacao> {
    Page<Movimentacao> findAllByProdutoId(Long produtoId, Pageable pageable);
}
