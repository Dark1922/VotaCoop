package com.br.VotaCoop.api.controller;

import com.br.VotaCoop.api.dto.Input.SessaoVotacaoInput;
import com.br.VotaCoop.domain.model.SessaoVotacao;
import com.br.VotaCoop.domain.service.SessaoVotacaoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/sessao_votacao")
@AllArgsConstructor
public class SessaoVotoController {

    private SessaoVotacaoService sessaoVotacaoService;
    @PostMapping
    public ResponseEntity<SessaoVotacao> iniciarSessaoVotacao(@RequestBody @Valid SessaoVotacaoInput sessaoVotacaoInput){
        return ResponseEntity.status(HttpStatus.CREATED).body(sessaoVotacaoService.iniciarSessao(sessaoVotacaoInput));
    }
}
