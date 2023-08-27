package com.br.VotaCoop.domain.service.impl;

import com.br.VotaCoop.api.assembler.VotoInputDissasembler;
import com.br.VotaCoop.api.assembler.VotoModelAssembler;
import com.br.VotaCoop.api.dto.Input.VotoInput;
import com.br.VotaCoop.api.dto.VotoDTO;
import com.br.VotaCoop.domain.exception.VotoNotFoundException;
import com.br.VotaCoop.domain.model.Voto;
import com.br.VotaCoop.domain.repository.VotoRepository;
import com.br.VotaCoop.domain.service.VotoService;

public class VotoServiceImpl implements VotoService {

    private static final String MSG_VOTO_NAO_ENCONTRADO = "Não encontrado com o código %d";

    private VotoRepository votoRepository;
    private VotoInputDissasembler votoInputDissasembler;
    private VotoModelAssembler votoModelAssembler;

    public VotoServiceImpl(VotoRepository votoRepository,
                           VotoInputDissasembler votoInputDissasembler,
                           VotoModelAssembler votoModelAssembler) {
        this.votoRepository = votoRepository;
        this.votoInputDissasembler = votoInputDissasembler;
        this.votoModelAssembler = votoModelAssembler;
    }
    @Override
    public VotoDTO findById(Long idVoto) {
        return votoModelAssembler.toModel(buscarOuFalhar(idVoto));
    }

    @Override
    public VotoDTO saveVoto(VotoInput votoInput) {
            Voto voto = votoInputDissasembler.toDomainObject(votoInput);
            return votoModelAssembler.toModel(votoRepository.save(voto));
        }

    public Voto buscarOuFalhar(Long id) {
        return votoRepository.findById(id)
                .orElseThrow(() -> new VotoNotFoundException(String.format(MSG_VOTO_NAO_ENCONTRADO, id)));
    }
}
