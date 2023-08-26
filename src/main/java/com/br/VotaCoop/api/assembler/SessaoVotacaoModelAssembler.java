package com.br.VotaCoop.api.assembler;

import com.br.VotaCoop.api.dto.SessaoVotacaoDTO;
import com.br.VotaCoop.domain.model.SessaoVotacao;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SessaoVotacaoModelAssembler {

    private final ModelMapper modelMapper;

    public SessaoVotacaoModelAssembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public SessaoVotacaoDTO toModel(SessaoVotacao sessaoVotacao) {
        return modelMapper.map(sessaoVotacao, SessaoVotacaoDTO.class);
    }
    public List<SessaoVotacaoDTO> toCollectionModel(List<SessaoVotacao> sessaoVotacaos) {
        return sessaoVotacaos.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }
    public Page<SessaoVotacaoDTO> toCollectionModelPage(Page<SessaoVotacao> page) {
        return page.map(this::toModel);
    }
}
