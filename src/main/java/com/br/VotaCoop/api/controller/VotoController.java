package com.br.VotaCoop.api.controller;

import com.br.VotaCoop.api.dto.Input.VotoInput;
import com.br.VotaCoop.api.dto.VotoDTO;
import com.br.VotaCoop.domain.service.VotoService;
import com.br.VotaCoop.openapi.VotoControllerOpenApi;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/voto")
@AllArgsConstructor
public class VotoController implements VotoControllerOpenApi {

   private VotoService votoService;

    @GetMapping("/{id}")
    public ResponseEntity<VotoDTO> findById(@PathVariable Long id){
        return ResponseEntity.ok(votoService.findById(id));
    }

    @PostMapping
    public ResponseEntity<VotoDTO> saveVoto(@RequestBody @Valid VotoInput votoInput){
        return ResponseEntity.status(HttpStatus.CREATED).body(votoService.saveVoto(votoInput));
    }

}
