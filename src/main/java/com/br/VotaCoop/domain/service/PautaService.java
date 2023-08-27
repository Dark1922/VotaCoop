package com.br.VotaCoop.domain.service;

import com.br.VotaCoop.api.dto.Input.PautaInput;
import com.br.VotaCoop.api.dto.PautaDTO;
import com.br.VotaCoop.domain.model.Pauta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PautaService {

     PautaDTO findById(Long idpauta);
     List<PautaDTO> findAll();
     Page<PautaDTO> findAllPage(Pageable pageable);
     PautaDTO saveVoto(PautaInput pautaInput);
     Page<PautaDTO> findByNomeContaining(String tema, Pageable pageable);

     Pauta buscarOuFalhar(Long id);
}
