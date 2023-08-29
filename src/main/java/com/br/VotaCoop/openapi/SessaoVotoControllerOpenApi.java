package com.br.VotaCoop.openapi;

import com.br.VotaCoop.api.dto.Input.SessaoVotacaoInput;
import com.br.VotaCoop.api.dto.ResultadoVotacaoDTO;
import com.br.VotaCoop.domain.model.SessaoVotacao;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "SessaoVotacao")
public interface SessaoVotoControllerOpenApi {

    @Operation(summary = "Cadastro de uma Sessão de voto", description = "Cadastro de uma Sessão de voto, " +
            "necessita de um id_pauta válido e valor de duração")
     ResponseEntity<SessaoVotacao> iniciarSessaoVotacao(@Parameter( description = "Representeção de uma sessão de voto",
            required = true) SessaoVotacaoInput sessaoVotacaoInput);

    @Operation(summary = "Busca o resultado montante da sessão de voto",
            responses = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "400", description = "ID da sessão de votação inválido",
                            content = @Content(schema = @Schema(ref = "Problema"))
                    ),
                    @ApiResponse(responseCode = "404", description = "Sessão de voto não encontrado",
                            content = @Content(schema = @Schema(ref = "Problema404"))
                    ),
                    @ApiResponse(responseCode = "406", description = "Formato de Resposta Não Suportado",
                            content = @Content(schema = @Schema(ref = "Problema406"))
                    ),
                    @ApiResponse(responseCode = "500", description = "Erro interno",
                            content = @Content(schema = @Schema(ref = "Problema500"))
                    )
            })
     ResponseEntity<ResultadoVotacaoDTO> getResultadoVotacao(@Parameter(description = "ID de uma sessão de votação",
            example = "1", required = true) Long idSessaoVotacao);

}
