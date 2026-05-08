package com.controle.estoque.controller;

import com.controle.estoque.dto.request.MovimentacaoRequest;
import com.controle.estoque.dto.response.MovimentacaoResponse;
import com.controle.estoque.service.MovimentacaoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/movimentacoes")
public class MovimentacaoController {

    private final MovimentacaoService movimentacaoService;

    public MovimentacaoController(MovimentacaoService movimentacaoService) {
        this.movimentacaoService = movimentacaoService;
    }

    @GetMapping("/produto/{produtoId}")
    public Page<MovimentacaoResponse> listarPorProduto(
            @PathVariable Long produtoId,
            @PageableDefault(size = 20) Pageable pageable) {
        return movimentacaoService.listarPorProduto(produtoId, pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MovimentacaoResponse registrar(@Valid @RequestBody MovimentacaoRequest req) {
        return movimentacaoService.registrar(req);
    }
}
