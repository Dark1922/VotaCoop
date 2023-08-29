package com.br.VotaCoop;

import com.br.VotaCoop.api.assembler.PautaInputDissasembler;
import com.br.VotaCoop.api.assembler.PautaModelAssembler;
import com.br.VotaCoop.api.dto.Input.PautaInput;
import com.br.VotaCoop.domain.exception.VotoNotFoundException;
import com.br.VotaCoop.domain.model.Pauta;
import com.br.VotaCoop.domain.repository.PautaRepository;
import com.br.VotaCoop.domain.service.impl.PautaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-teste.properties")
class PautaServiceImplTest {

    @InjectMocks
    private PautaServiceImpl pautaService;

    @Mock
    private PautaRepository pautaRepository;

    @Mock
    private PautaInputDissasembler pautaInputDissasembler;

    @Mock
    private PautaModelAssembler pautaModelAssembler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testFindById() {
        Pauta pauta = new Pauta();
        when(pautaRepository.findById(1L)).thenReturn(Optional.of(pauta));

        pautaService.findById(1L);

        verify(pautaRepository).findById(1L);
    }

    @Test
    void testFindAll() {
        pautaService.findAll();

        verify(pautaRepository).findAll();
    }

    @Test
    void testFindAllPage() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Pauta> pautaPage = mock(Page.class);
        when(pautaRepository.findAll(pageable)).thenReturn(pautaPage);

        pautaService.findAllPage(pageable);

        verify(pautaRepository).findAll(pageable);
    }

    @Test
    void testSaveVoto() {
        // Mockando o PautaInput e o objeto Pauta
        PautaInput pautaInputMock = mock(PautaInput.class);
        Pauta pautaMock = mock(Pauta.class);
        Pauta savedPautaMock = mock(Pauta.class);

        // Quando o pautaInputDissasembler.toDomainObject for chamado, retorne o pautaMock
        when(pautaInputDissasembler.toDomainObject(pautaInputMock)).thenReturn(pautaMock);

        // Quando o pautaRepository.save for chamado, retorne o savedPautaMock
        when(pautaRepository.save(pautaMock)).thenReturn(savedPautaMock);

        // Chama o método saveVoto
        pautaService.saveVoto(pautaInputMock);

        // Verifica se os métodos apropriados foram chamados
        verify(pautaInputDissasembler).toDomainObject(pautaInputMock);
        verify(pautaRepository).save(pautaMock);
        verify(pautaModelAssembler).toModel(savedPautaMock);
    }

    @Test
    void testFindByNomeContaining() {
        Pageable pageable = PageRequest.of(0, 10);
        pautaService.findByNomeContaining("tema", pageable);
        verify(pautaRepository).findByTemaContaining("TEMA", pageable);
    }

    @Test
    void testBuscarOuFalhar() {
        when(pautaRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(VotoNotFoundException.class, () -> pautaService.buscarOuFalhar(1L));
    }
}
