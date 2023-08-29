package com.br.VotaCoop.api.assembler;

import com.br.VotaCoop.api.dto.VotoDTO;
import com.br.VotaCoop.domain.model.Voto;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class VotoModelAssembler {

    private final ModelMapper modelMapper;

    public VotoModelAssembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public VotoDTO toModel(Voto voto) {
        return modelMapper.map(voto, VotoDTO.class);
    }
    public List<VotoDTO> toCollectionModel(List<Voto> votos) {
        return votos.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }
    public Page<VotoDTO> toCollectionModelPage(Page<Voto> page) {
        return page.map(this::toModel);
    }
}
