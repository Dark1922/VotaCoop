package com.br.VotaCoop;

import com.br.VotaCoop.api.assembler.VotoModelAssembler;
import com.br.VotaCoop.api.dto.Input.VotoInput;
import com.br.VotaCoop.api.dto.VotoDTO;
import com.br.VotaCoop.domain.model.Voto;
import com.br.VotaCoop.domain.repository.VotoRepository;
import com.br.VotaCoop.domain.service.AssociadoService;
import com.br.VotaCoop.domain.service.SessaoVotacaoService;
import com.br.VotaCoop.domain.service.impl.VotoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
public class VotoServiceImplTest {

    @InjectMocks
    private VotoServiceImpl votoService;

    @Mock
    private VotoRepository votoRepository;

    @Mock
    private VotoModelAssembler votoModelAssembler;

    @Mock
    private AssociadoService associadoService;

    @Mock
    private SessaoVotacaoService sessaoVotacaoService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindById() {
        Long id = 1L;
        Voto voto = new Voto();
        VotoDTO votoDTO = new VotoDTO();

        when(votoRepository.findById(id)).thenReturn(java.util.Optional.of(voto));
        when(votoModelAssembler.toModel(voto)).thenReturn(votoDTO);

        VotoDTO result = votoService.findById(id);

        assertEquals(votoDTO, result);
    }

    @Test
    public void testSaveVoto() {
        VotoInput votoInput = new VotoInput();
        votoInput.setIdSessaoVotacao(1L);
        votoInput.setIdAssociado(1L);
        Voto voto = new Voto();
        VotoDTO votoDTO = new VotoDTO();

        when(sessaoVotacaoService.isSessaoVotacaoAberta(votoInput.getIdSessaoVotacao())).thenReturn(true);
        when(votoRepository.existsBySessaoVotacaoIdAndAssociadoId(votoInput.getIdSessaoVotacao(), votoInput.getIdAssociado())).thenReturn(false);
        when(votoRepository.save(any(Voto.class))).thenReturn(voto);
        when(votoModelAssembler.toModel(voto)).thenReturn(votoDTO);

        VotoDTO result = votoService.saveVoto(votoInput);

        assertEquals(votoDTO, result);
    }
    @Test
    public void testBuscarOuFalhar() {
        Long id = 1L;
        Voto voto = new Voto();

        when(votoRepository.findById(id)).thenReturn(java.util.Optional.of(voto));

        Voto result = votoService.buscarOuFalhar(id);

        assertEquals(voto, result);
    }

}
