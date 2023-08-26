package com.br.VotaCoop.api.assembler;

import com.br.VotaCoop.api.dto.Input.PautaInput;
import com.br.VotaCoop.domain.model.Pauta;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class PautaInputDissasembler {

    private final ModelMapper modelMapper;

    public PautaInputDissasembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Pauta toDomainObject(PautaInput pautaInput) {
        return modelMapper.map(pautaInput, Pauta.class);
    }

    public void copyToDomainObject(PautaInput pautaInput, Pauta pauta) {
        modelMapper.map(pautaInput, pauta);
    }
}
