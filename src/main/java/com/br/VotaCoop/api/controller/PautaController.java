package com.br.VotaCoop.api.controller;

import com.br.VotaCoop.api.dto.Input.PautaInput;
import com.br.VotaCoop.api.dto.PautaDTO;
import com.br.VotaCoop.domain.service.PautaService;
import com.br.VotaCoop.openapi.PautaControllerOpenApi;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/pauta")
@AllArgsConstructor
public class PautaController implements PautaControllerOpenApi {

    private PautaService pautaService;

    @GetMapping("/page")
    public ResponseEntity<Page<PautaDTO>> listarPautasPage(
            @PageableDefault(size = 5, sort = "tema") Pageable pageable) {
        return ResponseEntity.ok(pautaService.findAllPage(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PautaDTO> findById(@PathVariable Long id){
        return ResponseEntity.ok(pautaService.findById(id));
    }

    @GetMapping("/find/{tema}/page")
    public ResponseEntity<Page<PautaDTO>> findByPautaPage(@PathVariable String tema, @PageableDefault(size = 5, sort = "tema") Pageable pageable) {
        return ResponseEntity.ok(pautaService.findByNomeContaining(tema, pageable));
    }

    @PostMapping
    public ResponseEntity<PautaDTO> savePauta(@RequestBody @Valid PautaInput pautaInput){
        return ResponseEntity.status(HttpStatus.CREATED).body(pautaService.saveVoto(pautaInput));
    }
}
