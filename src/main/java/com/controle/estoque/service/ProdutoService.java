package com.controle.estoque.service;

import com.controle.estoque.dto.request.ProdutoRequest;
import com.controle.estoque.dto.response.ProdutoResponse;
import com.controle.estoque.entity.Produto;
import com.controle.estoque.exception.ResourceNotFoundException;
import com.controle.estoque.repository.ProdutoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public Page<ProdutoResponse> listar(Pageable pageable) {
        return produtoRepository.findAllByAtivoTrue(pageable).map(ProdutoResponse::from);
    }

    public ProdutoResponse buscarPorId(Long id) {
        return ProdutoResponse.from(buscarEntidade(id));
    }

    @Transactional
    public ProdutoResponse criar(ProdutoRequest req) {
        var produto = Produto.builder()
                .nome(req.nome())
                .descricao(req.descricao())
                .quantidade(req.quantidade())
                .preco(req.preco())
                .build();
        return ProdutoResponse.from(produtoRepository.save(produto));
    }

    @Transactional
    public ProdutoResponse atualizar(Long id, ProdutoRequest req) {
        var produto = buscarEntidade(id);
        produto.atualizar(req.nome(), req.descricao(), req.preco());
        return ProdutoResponse.from(produtoRepository.save(produto));
    }

    @Transactional
    public void inativar(Long id) {
        var produto = buscarEntidade(id);
        produtoRepository.delete(produto);
    }

    Produto buscarEntidade(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto", id));
    }

    Produto salvar(Produto produto) {
        return produtoRepository.save(produto);
    }
}
