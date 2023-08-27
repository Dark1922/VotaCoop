package com.br.VotaCoop.domain.service.impl;

import com.br.VotaCoop.api.assembler.PautaInputDissasembler;
import com.br.VotaCoop.api.assembler.PautaModelAssembler;
import com.br.VotaCoop.api.dto.Input.PautaInput;
import com.br.VotaCoop.api.dto.PautaDTO;
import com.br.VotaCoop.domain.exception.VotoNotFoundException;
import com.br.VotaCoop.domain.model.Pauta;
import com.br.VotaCoop.domain.repository.PautaRepository;
import com.br.VotaCoop.domain.service.PautaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PautaServiceImpl implements PautaService {

    private static final String MSG_PAUTA_NAO_ENCONTRADO = "Não existe um cadastro de Pauta com código %d";

    private PautaRepository pautaRepository;
    private PautaInputDissasembler pautaInputDissasembler;
    private PautaModelAssembler pautaModelAssembler;

    public PautaServiceImpl(PautaRepository pautaRepository,
                            PautaInputDissasembler pautaInputDissasembler,
                            PautaModelAssembler pautaModelAssembler) {
        this.pautaRepository = pautaRepository;
        this.pautaInputDissasembler = pautaInputDissasembler;
        this.pautaModelAssembler = pautaModelAssembler;
    }
    @Override
    public PautaDTO findById(Long idPauta) {
        return pautaModelAssembler.toModel(buscarOuFalhar(idPauta));
    }

    public List<PautaDTO> findAll() {
        return pautaModelAssembler.toCollectionModel(pautaRepository.findAll());
    }

    public Page<PautaDTO> findAllPage(Pageable pageable) {
        Page<Pauta> pautassPage = pautaRepository.findAll(pageable);
        List<PautaDTO> pautasDTO = pautaModelAssembler.toCollectionModel(pautassPage.getContent());
        Page<PautaDTO> pautasDTOPage = new PageImpl<>(pautasDTO, pageable, pautassPage.getTotalElements());
        return pautasDTOPage;
    }
    @Override
    public PautaDTO saveVoto(PautaInput pautaInput) {
            Pauta pauta = pautaInputDissasembler.toDomainObject(pautaInput);
            return pautaModelAssembler.toModel(pautaRepository.save(pauta));
        }

    @Override
    public Page<PautaDTO> findByNomeContaining(String tema, Pageable pageable) {
        return pautaModelAssembler.toCollectionModelPage(pautaRepository.findByTemaContaining(tema.toUpperCase(), pageable));
    }
    public Pauta buscarOuFalhar(Long id) {
        return pautaRepository.findById(id)
                .orElseThrow(() -> new VotoNotFoundException(String.format(MSG_PAUTA_NAO_ENCONTRADO, id)));
    }
}
