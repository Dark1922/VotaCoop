package com.br.VotaCoop;

import com.br.VotaCoop.api.dto.Input.SessaoVotacaoInput;
import com.br.VotaCoop.domain.repository.SessaoVotacaoRepository;
import com.br.VotaCoop.domain.service.impl.SessaoVotacaoServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-teste.properties")
public class SessaoVotacaoServiceImplTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    SessaoVotacaoRepository sessaoVotacaoRepository;

    @Autowired
    SessaoVotacaoServiceImpl service;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testIniciarSessaoEndpoint() throws Exception {
        SessaoVotacaoInput input = new SessaoVotacaoInput();
        input.setIdPauta(1L);

        mockMvc.perform(MockMvcRequestBuilders.post("/sessao_votacao")
                        .content(asJsonString(input))
                        .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"));
    }


    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testGetResultadoVotacao() {
        SessaoVotacaoInput input = new SessaoVotacaoInput();

        try {
            mockMvc.perform(MockMvcRequestBuilders.get("/sessao_votacao/1/resultado")
                            .content(asJsonString(input))
                            .contentType("application/json"))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.content().contentType("application/json"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}