package com.br.VotaCoop.domain.service.impl;

import com.br.VotaCoop.api.assembler.AssociadoInputDissasembler;
import com.br.VotaCoop.api.assembler.AssociadoModelAssembler;
import com.br.VotaCoop.api.dto.AssociadoDTO;
import com.br.VotaCoop.api.dto.Input.AssociadoInput;
import com.br.VotaCoop.domain.exception.AssociadoNotFoundException;
import com.br.VotaCoop.domain.exception.ValidationException;
import com.br.VotaCoop.domain.model.Associado;
import com.br.VotaCoop.domain.repository.AssociadoRepository;
import com.br.VotaCoop.domain.service.AssociadoService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssociadoServiceImpl implements AssociadoService {

    private static final String MSG_ASSOCIADO_NAO_ENCOTNADO = "Não existe um cadastro de Associado com código %d";
    private static final String CPF_JA_CADASTRO = "O CPF informado já foi cadastrado no sistema. %s";
    private static final String CPF_EM_USO = "O CPF já está em uso por outro usuário. %s";

    private AssociadoRepository associadoRepository;
    private AssociadoInputDissasembler associadoInputDissasembler;
    private AssociadoModelAssembler associadoModelAssembler;

    public AssociadoServiceImpl(AssociadoRepository associadoRepository,
                                AssociadoInputDissasembler associadoInputDissasembler,
                                AssociadoModelAssembler associadoModelAssembler) {
        this.associadoRepository = associadoRepository;
        this.associadoInputDissasembler = associadoInputDissasembler;
        this.associadoModelAssembler = associadoModelAssembler;
    }

    public List<AssociadoDTO> findAll() {
        return associadoModelAssembler.toCollectionModel(associadoRepository.findAll());
    }

    public Page<AssociadoDTO> findAllPage(Pageable pageable) {
        Page<Associado> associadosPage = associadoRepository.findAll(pageable);
        List<AssociadoDTO> associadoDTO = associadoModelAssembler.toCollectionModel(associadosPage.getContent());
        Page<AssociadoDTO> associadoDTOPage = new PageImpl<>(associadoDTO, pageable, associadosPage.getTotalElements());
        return associadoDTOPage;
    }
    @Override
    public AssociadoDTO findById(Long idAssociado) {
        return associadoModelAssembler.toModel(buscarOuFalhar(idAssociado));
    }
    @Override
    public AssociadoDTO saveAssociado(AssociadoInput associadoInput) {

        if(associadoRepository.existsByCpf(associadoInput.getCpf())) {
            throw new ValidationException(String.format(CPF_JA_CADASTRO, associadoInput.getCpf()));
        }
            Associado associado = associadoInputDissasembler.toDomainObject(associadoInput);
            return associadoModelAssembler.toModel(associadoRepository.save(associado));
    }
    @Override
    public AssociadoDTO updateAssociado(Long idAssociado, AssociadoInput associadoInput) {
        Associado associadoAtual = buscarOuFalhar(idAssociado);

        // Verifica se o CPF é diferente e já existe no banco de dados
        if (!associadoInput.getCpf().equals(associadoAtual.getCpf())
                && associadoRepository.existsByCpf(associadoInput.getCpf())) {
            throw new ValidationException(String.format(CPF_EM_USO, associadoInput.getCpf()));
        }

        associadoInputDissasembler.copyToDomainObject(associadoInput, associadoAtual);
        return associadoModelAssembler.toModel(associadoRepository.save(associadoAtual));
    }

    @Override
    public void delete(Long idAssociado) {
        try {
            if(buscarOuFalhar(idAssociado) != null) {
                associadoRepository.deleteById(idAssociado);
            }
        } catch(EmptyResultDataAccessException e){
            throw new AssociadoNotFoundException(String.format(MSG_ASSOCIADO_NAO_ENCOTNADO, idAssociado));
        }
    }

    public Associado buscarOuFalhar(Long id) {
        return associadoRepository.findById(id)
                .orElseThrow(() -> new AssociadoNotFoundException(String.format(MSG_ASSOCIADO_NAO_ENCOTNADO, id)));
    }
}
