package com.controle.estoque.service;

import com.controle.estoque.dto.request.MovimentacaoRequest;
import com.controle.estoque.dto.response.MovimentacaoResponse;
import com.controle.estoque.entity.Movimentacao;
import com.controle.estoque.exception.BusinessException;
import com.controle.estoque.repository.MovimentacaoRepository;
import com.controle.estoque.repository.MovimentacaoSpec;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class MovimentacaoService {

    private final MovimentacaoRepository movimentacaoRepository;
    private final ProdutoService produtoService;

    public MovimentacaoService(MovimentacaoRepository movimentacaoRepository, ProdutoService produtoService) {
        this.movimentacaoRepository = movimentacaoRepository;
        this.produtoService = produtoService;
    }

    public Page<MovimentacaoResponse> listarPorProduto(Long produtoId, Pageable pageable) {
        return movimentacaoRepository.findAllByProdutoId(produtoId, pageable).map(MovimentacaoResponse::from);
    }

    @Transactional(readOnly = true)
    public Page<MovimentacaoResponse> listarHistorico(Long produtoId, Movimentacao.Tipo tipo, Instant de, Instant ate, Pageable pageable) {
        return movimentacaoRepository.findAll(MovimentacaoSpec.filtrar(produtoId, tipo, de, ate), pageable)
                .map(MovimentacaoResponse::from);
    }

    @Transactional
    public MovimentacaoResponse registrar(MovimentacaoRequest req) {
        var produto = produtoService.buscarEntidade(req.produtoId());
        int delta = req.tipo() == Movimentacao.Tipo.ENTRADA ? req.quantidade() : -req.quantidade();

        if (produto.getQuantidade() + delta < 0) {
            throw new BusinessException("Estoque insuficiente. Disponível: " + produto.getQuantidade());
        }

        produto.ajustarEstoque(delta);
        produtoService.salvar(produto);
        var movimentacao = Movimentacao.builder()
                .produto(produto)
                .tipo(req.tipo())
                .quantidade(req.quantidade())
                .observacao(req.observacao())
                .build();
        return MovimentacaoResponse.from(movimentacaoRepository.save(movimentacao));
    }
}
