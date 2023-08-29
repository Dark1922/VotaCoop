package com.br.VotaCoop.api.controller;

import com.br.VotaCoop.api.dto.AssociadoDTO;
import com.br.VotaCoop.api.dto.Input.AssociadoInput;
import com.br.VotaCoop.domain.service.AssociadoService;
import com.br.VotaCoop.openapi.AssociadoControllerOpenApi;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/associado")
@AllArgsConstructor
public class AssociadoController implements AssociadoControllerOpenApi {

    private AssociadoService associadoService;

    @GetMapping
    public ResponseEntity<List<AssociadoDTO>> findAll(){
        return ResponseEntity.ok(associadoService.findAll());
    }

    @GetMapping("/page")
    public ResponseEntity<Page<AssociadoDTO>> listarAssociadosPage(
            @PageableDefault(size = 5, sort = "nome") Pageable pageable) {
        return ResponseEntity.ok(associadoService.findAllPage(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssociadoDTO> findById(@PathVariable Long id){
        return ResponseEntity.ok(associadoService.findById(id));
    }

    @PostMapping
    public ResponseEntity<AssociadoDTO> saveAssociado(@RequestBody @Valid AssociadoInput associadoInput){
        return ResponseEntity.status(HttpStatus.CREATED).body(associadoService.saveAssociado(associadoInput));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AssociadoDTO> updateAssociado(@PathVariable Long id, @RequestBody @Valid AssociadoInput associadoInput){
        return ResponseEntity.status(HttpStatus.CREATED).body(associadoService.updateAssociado(id, associadoInput));
    }
}
