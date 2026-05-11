package com.controle.estoque.controller;

import com.controle.estoque.dto.request.MovimentacaoRequest;
import com.controle.estoque.dto.response.MovimentacaoResponse;
import com.controle.estoque.entity.Movimentacao;
import com.controle.estoque.service.MovimentacaoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping("/api/movimentacoes")
public class MovimentacaoController {

    private final MovimentacaoService movimentacaoService;

    public MovimentacaoController(MovimentacaoService movimentacaoService) {
        this.movimentacaoService = movimentacaoService;
    }

    @GetMapping("/historico")
    @PreAuthorize("hasRole('ADMIN')")
    public Page<MovimentacaoResponse> listarHistorico(
            @RequestParam(required = false) Long produtoId,
            @RequestParam(required = false) Movimentacao.Tipo tipo,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant de,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant ate,
            @PageableDefault(size = 20, sort = "criadoEm") Pageable pageable) {
        return movimentacaoService.listarHistorico(produtoId, tipo, de, ate, pageable);
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
