package com.br.VotaCoop.api.assembler;

import com.br.VotaCoop.api.dto.PautaDTO;
import com.br.VotaCoop.domain.model.Pauta;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PautaModelAssembler {

    private final ModelMapper modelMapper;

    public PautaModelAssembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public PautaDTO toModel(Pauta pauta) {
        return modelMapper.map(pauta, PautaDTO.class);
    }
    public List<PautaDTO> toCollectionModel(List<Pauta> Pautas) {
        return Pautas.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }
    public Page<PautaDTO> toCollectionModelPage(Page<Pauta> page) {
        return page.map(this::toModel);
    }
}
