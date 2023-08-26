package com.br.VotaCoop.api.assembler;

import com.br.VotaCoop.api.dto.Input.AssociadoInput;
import com.br.VotaCoop.domain.model.Associado;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class AssociadoInputDissasembler {

    private final ModelMapper modelMapper;

    public AssociadoInputDissasembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Associado toDomainObject(AssociadoInput associadoInput) {
        return modelMapper.map(associadoInput, Associado.class);
    }

    public void copyToDomainObject(AssociadoInput associadoInput, Associado associado) {
        modelMapper.map(associadoInput, associado);
    }
}
