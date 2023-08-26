package com.br.VotaCoop.api.assembler;

import com.br.VotaCoop.api.dto.Input.VotoInput;
import com.br.VotaCoop.domain.model.Voto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class VotoInputDissasembler {

    private final ModelMapper modelMapper;

    public VotoInputDissasembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Voto toDomainObject(VotoInput votoInput) {
        return modelMapper.map(votoInput, Voto.class);
    }

    public void copyToDomainObject(VotoInput votoInput, Voto voto) {
        modelMapper.map(votoInput, voto);
    }
}
