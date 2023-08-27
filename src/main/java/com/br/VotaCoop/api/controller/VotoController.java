package com.br.VotaCoop.api.controller;

import com.br.VotaCoop.api.dto.Input.VotoInput;
import com.br.VotaCoop.api.dto.ResultadoVotacaoDTO;
import com.br.VotaCoop.api.dto.VotoDTO;
import com.br.VotaCoop.domain.service.VotoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/voto")
@AllArgsConstructor
public class VotoController {

   private VotoService votoService;

    @GetMapping("/{id}")
    public ResponseEntity<VotoDTO> findById(@PathVariable Long id){
        return ResponseEntity.ok(votoService.findById(id));
    }

    @PostMapping
    public ResponseEntity<VotoDTO> saveVoto(@RequestBody @Valid VotoInput votoInput){
        return ResponseEntity.status(HttpStatus.CREATED).body(votoService.saveVoto(votoInput));
    }

    @GetMapping("/{idPauta}/resultado")
    public ResponseEntity<ResultadoVotacaoDTO> getResultadoVotacao(@PathVariable Long idPauta) {
        ResultadoVotacaoDTO resultado = votoService.getResultadoVotacao(idPauta);
        return ResponseEntity.ok(resultado);
    }
}