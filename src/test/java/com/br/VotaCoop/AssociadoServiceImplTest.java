package com.br.VotaCoop;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.br.VotaCoop.api.assembler.AssociadoInputDissasembler;
import com.br.VotaCoop.api.assembler.AssociadoModelAssembler;
import com.br.VotaCoop.api.dto.AssociadoDTO;
import com.br.VotaCoop.api.dto.Input.AssociadoInput;
import com.br.VotaCoop.domain.exception.AssociadoNotFoundException;
import com.br.VotaCoop.domain.model.Associado;
import com.br.VotaCoop.domain.repository.AssociadoRepository;
import com.br.VotaCoop.domain.service.impl.AssociadoServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-teste.properties")
public class AssociadoServiceImplTest {

    @InjectMocks
    private AssociadoServiceImpl associadoService;

    @Mock
    private AssociadoRepository associadoRepository;

    @Mock
    private AssociadoInputDissasembler associadoInputDissasembler;

    @Mock
    private AssociadoModelAssembler associadoModelAssembler;

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
    }

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = "/associado";
    }

    @Autowired
    private MockMvc mockMvc;

    private static final int ASSOCIADO_ID_INEXISTENTE = 100;

    @Test
    public void testSaveAssociado() {

        AssociadoInput input = new AssociadoInput();
        Associado associado = new Associado();
        when(associadoInputDissasembler.toDomainObject(input)).thenReturn(associado);
        when(associadoRepository.save(associado)).thenReturn(associado);

        associadoService.saveAssociado(input);

        verify(associadoRepository, times(1)).save(associado);
    }

    @Test
    public void testBuscarOuFalhar() {
        Long id = 1L;
        Associado associado = new Associado();
        associado.setNome("Luiz da silva");
        associado.setCpf("12345678901");
        when(associadoRepository.findById(id)).thenReturn(java.util.Optional.of(associado));
        assertEquals(associado, associadoService.buscarOuFalhar(id));
    }

    @Test
    public void testBuscarOuFalharIdNaoEncontrado() {
        Long id = 999999999999L;
        when(associadoRepository.findById(id)).thenReturn(Optional.empty());
        Exception exception = assertThrows(AssociadoNotFoundException.class, () -> associadoService.buscarOuFalhar(id));
        assertTrue(exception.getMessage().contains("Não existe um cadastro de Associado com código 999999999999"));
    }

    @Test
    public void testSalvarComNomeNull() throws Exception {
        AssociadoInput associado = new AssociadoInput();
        associado.setNome(null);
        associado.setCpf("12345678901");

        mockMvc.perform(post("/associado")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(associado)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.detail").value("Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente."))
                .andExpect(jsonPath("$.userMessage").value("Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente."));

    }

    @Test
    public void testSalvarComCpfNull() throws Exception {
        AssociadoInput associado = new AssociadoInput();
        associado.setNome("teste");
        associado.setCpf(null);

        mockMvc.perform(post("/associado")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(associado)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.detail").value("Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente."))
                .andExpect(jsonPath("$.userMessage").value("Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente."));

    }

    @Test
    public void testFindAll() {
        List<Associado> associados = Arrays.asList(new Associado(), new Associado());
        when(associadoRepository.findAll()).thenReturn(associados);
        when(associadoModelAssembler.toCollectionModel(associados)).thenReturn(new ArrayList<>()); // Adicione esta linha

        List<AssociadoDTO> result = associadoService.findAll();

        assertNotNull(result);
        verify(associadoRepository, times(1)).findAll();
    }

    @Test
    public void testFindAllPage() {
        Page<Associado> associados = new PageImpl<>(Arrays.asList(new Associado(), new Associado()));
        Pageable pageable = PageRequest.of(0, 2);
        when(associadoRepository.findAll(pageable)).thenReturn(associados);
        when(associadoModelAssembler.toCollectionModel(associados.getContent())).thenReturn(new ArrayList<>()); // Adicione esta linha

        Page<AssociadoDTO> result = associadoService.findAllPage(pageable);

        assertNotNull(result.getContent());
        verify(associadoRepository, times(1)).findAll(pageable);
    }

    /*Testes de integrações*/
    @Test
    public void testGetAssociadoById() {
        given()
                .pathParam("idAssociado", 1)
                .accept("application/json")
                .when()
                .get("/{idAssociado}")
                .then()
                .statusCode(200)
                .body("nome", equalTo("Luiz da silva"))
                .body("cpf", equalTo("12345678901"));
    }

    @Test
    public void deveRetornarStatus404_QuandoConsultarAssociadoInexistente() {
        given()
                .pathParam("idAssociado", ASSOCIADO_ID_INEXISTENTE)
                .accept(ContentType.JSON)
                .when()
                .get("/{idAssociado}")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }
}


