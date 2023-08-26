package com.br.VotaCoop.api.assembler;

import com.br.VotaCoop.api.dto.Input.SessaoVotacaoInput;
import com.br.VotaCoop.domain.model.SessaoVotacao;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class SessaoVotacaoInputDissasembler {

    private final ModelMapper modelMapper;

    public SessaoVotacaoInputDissasembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public SessaoVotacao toDomainObject(SessaoVotacaoInput sessaoVotacaoInput) {
        return modelMapper.map(sessaoVotacaoInput, SessaoVotacao.class);
    }

    public void copyToDomainObject(SessaoVotacaoInput sessaoVotacaoInput, SessaoVotacao sessaoVotacao) {
        modelMapper.map(sessaoVotacaoInput, sessaoVotacao);
    }
}
