package com.br.VotaCoop.domain.service;

import com.br.VotaCoop.api.dto.Input.VotoInput;
import com.br.VotaCoop.api.dto.VotoDTO;


public interface VotoService {

     VotoDTO findById(Long idVoto);
     VotoDTO saveVoto(VotoInput votoInput);
}
