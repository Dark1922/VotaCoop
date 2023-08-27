package com.br.VotaCoop.domain.service;

import com.br.VotaCoop.api.dto.AssociadoDTO;
import com.br.VotaCoop.api.dto.Input.AssociadoInput;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AssociadoService {

    List<AssociadoDTO> findAll();
    Page<AssociadoDTO> findAllPage(Pageable pageable);
     AssociadoDTO findById(Long idAssociado);
     AssociadoDTO saveAssociado(AssociadoInput associadoInput);
     AssociadoDTO updateAssociado(Long idAssociado, AssociadoInput associadoInput);
     void delete(Long idAssociado);

}
