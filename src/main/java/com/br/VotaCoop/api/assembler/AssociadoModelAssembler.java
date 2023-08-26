package com.br.VotaCoop.api.assembler;

import com.br.VotaCoop.api.dto.AssociadoDTO;
import com.br.VotaCoop.domain.model.Associado;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AssociadoModelAssembler {

    private final ModelMapper modelMapper;

    public AssociadoModelAssembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public AssociadoDTO toModel(Associado associado) {
        return modelMapper.map(associado, AssociadoDTO.class);
    }
    public List<AssociadoDTO> toCollectionModel(List<Associado> associados) {
        return associados.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }
    public Page<AssociadoDTO> toCollectionModelPage(Page<Associado> page) {
        return page.map(this::toModel);
    }
}
